package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.worker8.redditapi.model.listing.RedditCommentDataType
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(commentPair: Pair<Int, RedditCommentDataType.RedditCommentData>) {
        val (level, redditCommentDataType) = commentPair
        itemView.apply {
            commentIndentation.text = " ".repeat(level)
            commentItemText.text = Html.fromHtml(Html.fromHtml(redditCommentDataType.body_html).toString()).trim()
            commentItemAuthor.text = redditCommentDataType.author
            commentItemPoint.text = redditCommentDataType.score.toString()
            commentItemDateTime.text = redditCommentDataType.created.toRelativeTimeString()
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_item, parent, false)
                .let { CommentViewHolder(it) }
    }
}
