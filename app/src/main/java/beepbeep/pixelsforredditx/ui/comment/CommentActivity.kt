package beepbeep.pixelsforredditx.ui.comment

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.ofType
import beepbeep.pixelsforredditx.preference.ThemePreference
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.listing.RedditCommentDataType
import com.worker8.redditapi.model.t1_comment.RedditReply
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_comment.*

class CommentActivity : AppCompatActivity() {

    val commentId by lazy { intent.getStringExtra(COMMENT_ID) }
    private val disposableBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        setupTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        setupViews()
        RedditApi().getComment(commentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (resultPair, fuelError) ->
                resultPair?.let {
                    val (titleListing, commentListing) = it
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
                fuelError?.printStackTrace()
            }, {
                it.printStackTrace()
            })
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
