package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.RedditLink
import io.reactivex.Observable

class HomeContract {
    interface Input {
        val loadMore: Observable<Unit>
        val retry: Observable<Unit>
        val subredditSelected: Observable<String>
        val randomSubredditSelected: Observable<String>
        fun isConnectedToInternet(): Boolean
    }

    interface ViewAction {
        fun showNoNetworkErrorSnackbar()
        fun dismissNoNetworkErrorSnackbar()
        fun showLoadingProgressBar(isLoading: Boolean)
        fun showBottomLoadingProgresBar(isLoading: Boolean)
        fun updateToolbarSubredditText(subreddit: String)
        fun navSetHightlight(subreddit: String)
    }

    data class ScreenState(val redditLinks: List<RedditLink> = listOf())
}
