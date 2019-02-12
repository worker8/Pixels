package beepbeep.pixelsforredditx.ui.home

import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import io.reactivex.Observable

class HomeContract {
    interface Input {
        val loadMore: Observable<Unit>
        val retry: Observable<Unit>
        val subredditSelected: Observable<String>
        val randomSubredditSelected: Observable<String>
        val aboutClicked: Observable<Unit>
        val nightModeCheckChanged: Observable<Boolean>
        val postClicked: Observable<String>
        fun isConnectedToInternet(): Boolean
    }

    interface ViewAction {
        fun showNoNetworkErrorSnackbar()
        fun dismissNoNetworkErrorSnackbar()
        fun showLoadingProgressBar(isLoading: Boolean)
        fun showBottomLoadingProgresBar(isLoading: Boolean)
        fun updateToolbarSubredditText(subreddit: String)
        fun navSetHighlight(subreddit: String)
        fun showGenericErrorMessage()
        fun navigateToAboutPage()
        fun reRenderOnThemeChange(isNightMode: Boolean)
        fun navigateToCommentActivity(commentId: String)
    }

    data class ScreenState(val redditLinks: List<RedditLinkObject> = listOf())
}
