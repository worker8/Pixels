package beepbeep.pixelsforreddit.home

class HomeContract {
    interface Input {
        fun isConnectedToInternet(): Boolean
    }

    interface ViewAction {
        fun showNoNetworkError()
        fun showLoadingProgressBar(isLoading:Boolean)
    }
}
