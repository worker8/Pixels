package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.RedditLink
import io.reactivex.Observable

class HomeContract {
    interface Input {
        val loadMore: Observable<Unit>
        val retry: Observable<Unit>
        fun isConnectedToInternet(): Boolean
    }

    interface ViewAction {
        fun showNoNetworkErrorSnackbar()
        fun dismissNoNetworkErrorSnackbar()
        fun showLoadingProgressBar(isLoading: Boolean)
        fun showBottomLoadingProgresBar(isLoading: Boolean)
    }

    data class ScreenState(val redditLinks: List<RedditLink> = listOf())
}
