package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.ofType
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.worker8.redditapi.model.listing.RedditCommentDataType
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(commentPair: Pair<Int, RedditCommentDataType>) {
        val (level, redditCommentDataType) = commentPair
        itemView.apply {
            //commentIndentation.scaleX = 1 + (level * 0.1f)
            redditCommentDataType.ofType<RedditCommentDataType.RedditCommentData> { _redditCommentData ->
                commentGuideline.setGuidelinePercent(level * 0.02f)
                commentItemText.text = Html.fromHtml(Html.fromHtml(_redditCommentData.body_html).toString()).trim()
                commentItemAuthor.text = _redditCommentData.author
                commentItemPoint.text = _redditCommentData.score.toString()
                commentItemDateTime.text = _redditCommentData.created.toRelativeTimeString()
            }

            redditCommentDataType.ofType<RedditCommentDataType.TMore> {
                commentGuideline.setGuidelinePercent(it.depth * 0.02f)
                commentItemText.text = "(read more...)"
            }

        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_item, parent, false)
                .let { CommentViewHolder(it) }
    }
}
