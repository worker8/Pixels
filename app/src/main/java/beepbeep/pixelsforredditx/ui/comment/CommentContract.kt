package beepbeep.pixelsforredditx.ui.comment

import com.worker8.redditapi.model.t1_comment.RedditReplyListingData
import com.worker8.redditapi.model.t3_link.RedditLinkListingData

class CommentContract {
    interface Input {

    }

    interface ViewAction {

    }

    data class ScreenState(val linkAndComments: Pair<RedditLinkListingData, RedditReplyListingData>? = null)
}
