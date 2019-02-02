package beepbeep.pixelsforredditx.ui.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.ofType
import beepbeep.pixelsforredditx.extension.visibility
import beepbeep.pixelsforredditx.preference.ThemePreference
import com.worker8.redditapi.model.listing.RedditCommentDataType
import com.worker8.redditapi.model.t1_comment.RedditReply
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
                it.linkAndComments?.let { _linkAndComments ->
                    val (titleListing, commentListing) = _linkAndComments
                    val flattenComments: List<CommentAdapter.CommentViewType> = RedditReply.flattenComments(commentListing).map { (level, redditCommentDataType) ->
                        redditCommentDataType.ofType<RedditCommentDataType.TMore> {
                            return@map CommentAdapter.CommentViewType.ItemViewMore(level to it)
                        }
                        return@map CommentAdapter.CommentViewType.Item(level to redditCommentDataType as RedditCommentDataType.RedditCommentData)
                    }
                    val dataRows = mutableListOf<CommentAdapter.CommentViewType>()
                    dataRows.add(CommentAdapter.CommentViewType.Header(titleListing))
                    if (flattenComments.isEmpty()) {
                        dataRows.add(CommentAdapter.CommentViewType.Empty())
                    } else {
                        dataRows.addAll(flattenComments)
                    }
                    commentRecyclerView.adapter = CommentAdapter().apply { submitList(dataRows) }
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
