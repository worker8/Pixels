package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.RedditApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeRepo(subreddit: String = "pics") {
    val redditApi = RedditApi()
    fun getMorePosts() = redditApi.getMorePosts()
    fun getMainThread() = AndroidSchedulers.mainThread()
    fun getBackgroundThread() = Schedulers.io()
}
