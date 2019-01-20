package beepbeep.pixelsforredditx.ui.comment

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import beepbeep.pixelsforredditx.extension.addTo
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.t1_comment.RedditCommentListingData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CommentActivity : AppCompatActivity() {

    val commentId by lazy { intent.getStringExtra(COMMENT_ID) }
    private val disposableBag = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RedditApi().getComment(commentId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ (resultPair, fuelError) ->
                resultPair?.let {
                    val (titleListing, commentListing) = it
                    //RedditCommentListingData.traverse(commentListing, 0)
                    val temp = RedditCommentListingData.flattenComments(commentListing)
                    temp.forEachIndexed { index, pair ->
                        val (level, comment) = pair
                        val spaces = "  ".repeat(level)
                        Log.d("ddw", "${spaces}>>: ${comment}")
                    }
                }
                fuelError?.printStackTrace()
            }, {
                it.printStackTrace()
            })
            .addTo(disposableBag)
    }

    companion object {
        val COMMENT_ID = "COMMENT_ID"
    }
}
