package beepbeep.pixelsforreddit.home

import com.worker8.redditapi.Listing
import io.reactivex.Observable

class HomeContract {
    interface Input {
        val loadMore: Observable<Unit>
        fun isConnectedToInternet(): Boolean
    }

    interface ViewAction {
        fun showNoNetworkError()
        fun showLoadingProgressBar(isLoading: Boolean)
        fun showBottomLoadingProgresBar(isLoading: Boolean)
    }

    data class Output(val redditPosts: Listing = Listing())
}
