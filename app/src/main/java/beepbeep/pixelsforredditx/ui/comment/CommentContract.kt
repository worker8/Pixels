package beepbeep.pixelsforredditx.ui.comment

class CommentContract {
    interface Input {

    }

    interface ViewAction {
        fun showLoadingProgressBar(isLoading: Boolean)
    }

    data class ScreenState(val dataRows: List<CommentAdapter.CommentViewType>? = null)

}
