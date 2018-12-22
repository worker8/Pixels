package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.RedditApi2
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.dean.jraw.paginators.SubredditPaginator

class HomeRepo2(subreddit: String = "pics") {
    val redditApi = RedditApi2()
    fun getPosts() = redditApi.getPosts()
    fun getMainThread() = AndroidSchedulers.mainThread()
    fun getBackgroundThread() = Schedulers.io()
}