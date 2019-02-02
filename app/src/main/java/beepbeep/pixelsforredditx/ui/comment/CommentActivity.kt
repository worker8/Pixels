package beepbeep.pixelsforredditx.ui.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.visibility
import beepbeep.pixelsforredditx.preference.ThemePreference
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {

    val commentId by lazy { intent.getStringExtra(COMMENT_ID) }
    private val disposableBag = CompositeDisposable()
    private val commentInput = object : CommentContract.Input {

    }
    private val commentViewAction = object : CommentContract.ViewAction {
        override fun showLoadingProgressBar(isLoading: Boolean) {
            commentProgressBar.visibility = isLoading.visibility()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        setupViews()

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

    private fun setupViews() {
    }

    private fun setupTheme() {
        if (ThemePreference.getThemePreference(this)) {
            setTheme(R.style.AppThemeDark)
        } else {
            setTheme(R.style.AppTheme)
        }
    }

    companion object {
        val COMMENT_ID = "COMMENT_ID"
    }
}
