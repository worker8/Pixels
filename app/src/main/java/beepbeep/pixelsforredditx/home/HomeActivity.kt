package beepbeep.pixelsforredditx.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.about.AboutActivity
import beepbeep.pixelsforredditx.common.SnackbarOnlyOne
import beepbeep.pixelsforredditx.extension.*
import beepbeep.pixelsforredditx.home.navDrawer.NavigationDrawerView
import beepbeep.pixelsforredditx.preference.RedditPreference
import beepbeep.pixelsforredditx.preference.ThemePreference
import beepbeep.pixelsforredditx.ui.comment.CommentActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main._navigation_night_mode.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.navigational_parent.*

class HomeActivity : AppCompatActivity() {
    private val adapter = HomeAdapter()
    private val noNetworkSnackbar = SnackbarOnlyOne()
    private val disposableBag = CompositeDisposable()
    private val retrySubject: PublishSubject<Unit> = PublishSubject.create()
    private lateinit var navDrawerView: NavigationDrawerView
    private val homeInput = object : HomeContract.Input {
        override val postClicked by lazy { adapter.postClickedObservable }
        override val nightModeCheckChanged by lazy { navDrawerView.nightModeCheckChanged }
        override val aboutClicked by lazy { navDrawerView.aboutButtonClick }
        override val randomSubredditSelected by lazy { navDrawerView.randomButtonClick }
        override val subredditSelected by lazy { navDrawerView.subredditChosen }
        override val retry = retrySubject.hide()
        override val loadMore by lazy { homeList.onBottomDetectedObservable }
        override fun isConnectedToInternet() = this@HomeActivity.isConnectedToInternet()
    }

    private val homeViewAction = object : HomeContract.ViewAction {
        override fun navigateToCommentActivity(commentId: String) {
            startActivity(Intent(this@HomeActivity, CommentActivity::class.java).apply {
                putExtra(CommentActivity.COMMENT_ID, commentId)
            })
        }

        override fun reRenderOnThemeChange(isNightMode: Boolean) {
            ThemePreference.saveThemePreference(this@HomeActivity, isNightMode)
            finish()
            startActivity(Intent(this@HomeActivity, HomeActivity::class.java))
        }

        override fun navigateToAboutPage() {
            startActivity(Intent(this@HomeActivity, AboutActivity::class.java))
        }

        override fun showGenericErrorMessage() {
            homeErrorMessage.visibility = View.VISIBLE
        }

        override fun navSetHighlight(subreddit: String) = navDrawerView.setHighlight(subreddit)
        override fun dismissNoNetworkErrorSnackbar() = noNetworkSnackbar.dismiss()
        override fun updateToolbarSubredditText(subreddit: String) {
            selectedSubredditToolbar.text = RedditPreference.getSelectedSubreddit(this@HomeActivity)
        }

        override fun showBottomLoadingProgresBar(isLoading: Boolean) {
            homeBottomProgressBar.visibility = isLoading.visibility()
        }

        override fun showLoadingProgressBar(isLoading: Boolean) {
            homeProgressBar.visibility = isLoading.visibility()
        }

        override fun showNoNetworkErrorSnackbar() {
            noNetworkSnackbar.show(
                view = homeRootView,
                resId = R.string.no_network,
                duration = Snackbar.LENGTH_INDEFINITE,
                actionResId = R.string.retry,
                actionCallback = {
                    retrySubject.onNext(Unit)
                }
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigational_parent)

        navDrawerView = NavigationDrawerView(homeDrawerLayout)
        val viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java) //getViewModel<HomeViewModel>().also { lifecycle.addObserver(it) }
        viewModel.apply {
            input = homeInput
            repo = HomeRepo(this@HomeActivity, RedditPreference.getSelectedSubreddit(this@HomeActivity))
            viewAction = homeViewAction
        }
        lifecycle.addObserver(viewModel)
        setupView()

        viewModel.screenState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { adapter.submitList(it.redditLinks) }
            .addTo(disposableBag)
    }

    override fun onDestroy() {
        super.onDestroy()
        disposableBag.dispose()
    }

    private fun setupTheme() {
        if (ThemePreference.getThemePreference(this)) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    private fun setupView() {
        selectedSubredditToolbar.text = RedditPreference.getSelectedSubreddit(this)
        adapter.apply {
            homeList.adapter = this
            homeList.addItemDecoration(DividerItemDecoration(homeList.context, DividerItemDecoration.VERTICAL).apply { setDrawable(resources.getDrawable(R.drawable.recycler_view_divider)) })
            homeList.initBottomDetectListener()
        }
        nightModeSwitch.isChecked = ThemePreference.getThemePreference(this)
    }
}
