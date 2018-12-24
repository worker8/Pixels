package beepbeep.pixelsforreddit.home

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
}
