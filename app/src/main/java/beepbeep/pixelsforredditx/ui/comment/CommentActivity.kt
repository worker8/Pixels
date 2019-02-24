package beepbeep.pixelsforredditx.ui.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.common.SnackbarOnlyOne
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.isConnectedToInternet
import beepbeep.pixelsforredditx.extension.visibility
import beepbeep.pixelsforredditx.preference.ThemePreference
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {
    private val noNetworkSnackbar = SnackbarOnlyOne()
    private val commentId by lazy { intent.getStringExtra(COMMENT_ID) }
    private val disposableBag = CompositeDisposable()
    private val retrySubject: PublishSubject<Unit> = PublishSubject.create()
    private val commentInput = object : CommentContract.Input {
        override val retry: Observable<Unit> = retrySubject.hide()
        override fun isConnectedToInternet() = this@CommentActivity.isConnectedToInternet()
    }
    private val commentViewAction = object : CommentContract.ViewAction {
        override fun showLoadingProgressBar(isLoading: Boolean) {
            commentProgressBar.visibility = isLoading.visibility()
        }

        override fun showNoNetworkErrorSnackbar() {
            noNetworkSnackbar.show(
                view = commentRootView,
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
        setContentView(R.layout.activity_comment)

        val viewModel = ViewModelProviders.of(this).get(CommentViewModel::class.java)
            .apply {
                commentId = this@CommentActivity.commentId
                input = commentInput
                repo = CommentRepo()
                viewAction = commentViewAction
            }

        lifecycle.addObserver(viewModel)

        viewModel.screenState
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                it.dataRows?.let { rows ->
                    commentRecyclerView.adapter = CommentAdapter().apply { submitList(rows) }
                }
            }
            .addTo(disposableBag)
    }

    private fun setupTheme() {
        if (ThemePreference.getThemePreference(this)) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    companion object {
        const val COMMENT_ID = "COMMENT_ID"
    }
}
