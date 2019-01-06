package beepbeep.pixelsforreddit

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import beepbeep.pixelsforreddit.common.SnackbarOnlyOne
import beepbeep.pixelsforreddit.extension.*
import beepbeep.pixelsforreddit.home.*
import beepbeep.pixelsforreddit.home.navDrawer.NavigationDrawerView
import beepbeep.pixelsforreddit.reddit_api.RedditPreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.navigational_parent.*


class HomeActivity : AppCompatActivity() {
    private val adapter = HomeAdapter()
    private val viewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, HomeViewModelFactory(input, HomeRepo(this, RedditPreference.getSelectedSubreddit(this)), viewAction)).get(HomeViewModel::class.java) //getViewModel<HomeViewModel>().also { lifecycle.addObserver(it) }
    }
    private val noNetworkSnackbar = SnackbarOnlyOne()
    private val disposableBag = CompositeDisposable()
    private val retrySubject: PublishSubject<Unit> = PublishSubject.create()
    private val input = object : HomeContract.Input {
        override val subredditSelected by lazy { navDrawerView.subredditChosen }
        override val retry = retrySubject.hide()
        override val loadMore by lazy { homeList.onBottomDetectedObservable }
        override fun isConnectedToInternet() = this@HomeActivity.isConnectedToInternet()
    }

    private val viewAction = object : HomeContract.ViewAction {
        override fun navSetHightlight(subreddit: String) = navDrawerView.setHightlight(subreddit)

        override fun updateToolbarSubredditText(subreddit: String) {
            selectedSubredditToolbar.text = RedditPreference.getSelectedSubreddit(this@HomeActivity)
        }

        override fun dismissNoNetworkErrorSnackbar() = noNetworkSnackbar.dismiss()

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

    val navDrawerView: NavigationDrawerView by lazy { NavigationDrawerView(homeDrawerLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.navigational_parent)
        LayoutInflater.from(this).inflate(R.layout.activity_home, contentSpace, true)
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

    private fun setupView() {
        selectedSubredditToolbar.text = RedditPreference.getSelectedSubreddit(this)
        adapter.apply {
            homeList.adapter = this
            homeList.addItemDecoration(DividerItemDecoration(homeList.context, DividerItemDecoration.VERTICAL).apply { setDrawable(resources.getDrawable(R.drawable.recycler_view_divider)) })
            homeList.initBottomDetectListener()
        }
    }
}
