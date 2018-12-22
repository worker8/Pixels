package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.RedditApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.dean.jraw.paginators.SubredditPaginator

class HomeRepo(subreddit: String = "pics") {
    lateinit var paginator: SubredditPaginator
    val redditApi = RedditApi()

    fun getUserlessPaginator() = redditApi.authRedditClient().map {
        paginator = SubredditPaginator(it).apply {
            setSubreddit(subreddit)
        }
        paginator
    }

    fun getSubmissions() = paginator.next()

    fun getMainThread() = AndroidSchedulers.mainThread()
    fun getBackgroundThread() = Schedulers.io()
}