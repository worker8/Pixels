package beepbeep.pixelsforredditx.ui.comment

import io.reactivex.Observable

class CommentContract {
    interface Input {
        fun isConnectedToInternet(): Boolean
        val retry: Observable<Unit>
    }

    interface ViewAction {
        fun showLoadingProgressBar(isLoading: Boolean)
        fun showNoNetworkErrorSnackbar()
    }

    data class ScreenState(val dataRows: List<CommentAdapter.CommentViewType>? = null)

}
