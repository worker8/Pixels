package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.RedditApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeRepo2(subreddit: String = "pics") {
    val redditApi = RedditApi()
    fun getPosts() = redditApi.getPosts()
    fun getMainThread() = AndroidSchedulers.mainThread()
    fun getBackgroundThread() = Schedulers.io()
}