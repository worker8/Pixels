package beepbeep.pixelsforredditx.ui.comment

import com.worker8.redditapi.RedditApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CommentRepo {
    private val redditApi = RedditApi()
    fun getComments(commentId: String) = redditApi.getComment(commentId)
    fun getMainThread(): Scheduler = AndroidSchedulers.mainThread()
    fun getBackgroundThread(): Scheduler = Schedulers.io()
}
