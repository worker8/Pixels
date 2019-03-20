package beepbeep.pixelsforredditx.home

import android.content.Context
import beepbeep.pixelsforredditx.preference.RedditPreference
import com.worker8.redditapi.api.RedditApiGetPosts
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.properties.Delegates

class HomeRepo(val context: Context) {

    var pageToken = ""

    var selectedSubreddit by Delegates.observable("pics") { _, _, _ ->
        //reset pageToken
        pageToken = ""
    }

    fun getMorePosts() = RedditApiGetPosts(selectedSubreddit, pageToken).call()
        .doOnSuccess { (listing, _) ->
            pageToken = listing?.value?.after ?: ""
        }

    fun getMainThread(): Scheduler = AndroidSchedulers.mainThread()
    fun getBackgroundThread(): Scheduler = Schedulers.io()

    fun saveSubredditSharedPreference(subreddit: String) =
        RedditPreference.saveSelectedSubreddit(context, subreddit)

    fun getSubredditSharedPreference() =
        RedditPreference.getSelectedSubreddit(context)
}
