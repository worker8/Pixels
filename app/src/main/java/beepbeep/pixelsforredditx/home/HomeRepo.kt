package beepbeep.pixelsforredditx.home

import android.content.Context
import beepbeep.pixelsforredditx.preference.RedditPreference
import com.worker8.redditapi.RedditApi
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeRepo(val context: Context, subreddit: String = "pics") {
    private var redditApi: RedditApi

    init {
        redditApi = RedditApi(subreddit)
    }

    fun getMorePosts() = redditApi.getMorePosts()
    fun getMainThread(): Scheduler = AndroidSchedulers.mainThread()
    fun getBackgroundThread(): Scheduler = Schedulers.io()

    fun selectSubreddit(subreddit: String) {
        redditApi = RedditApi(subreddit)
    }

    fun saveSubredditSharedPreference(subreddit: String) =
        RedditPreference.saveSelectedSubreddit(context, subreddit)

    fun getSubredditSharedPreference() =
        RedditPreference.getSelectedSubreddit(context)
}
