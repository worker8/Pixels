package beepbeep.pixelsforredditx.ui.comment

import com.worker8.redditapi.api.RedditApiGetComments
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CommentRepo @Inject constructor() {
    fun getComments(commentId: String) = RedditApiGetComments(commentId).call()
    fun getMainThread(): Scheduler = AndroidSchedulers.mainThread()
    fun getBackgroundThread(): Scheduler = Schedulers.io()
}
