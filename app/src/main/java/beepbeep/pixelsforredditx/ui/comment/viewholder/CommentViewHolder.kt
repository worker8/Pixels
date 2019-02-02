package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.ui.comment.CommentAdapter
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(commentRow: CommentAdapter.CommentViewType.Item) {
        itemView.apply {
            commentIndentation.text = commentRow.level
            commentItemText.text = Html.fromHtml(Html.fromHtml(commentRow.commentHtmlString).toString()).trim()
            commentItemAuthor.text = commentRow.concatenatedInfoString //redditCommentDataType.author
//            commentItemPoint.text = redditCommentDataType.score.toString()
//            commentItemDateTime.text = redditCommentDataType.created.toRelativeTimeString()
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_item, parent, false)
                .let { CommentViewHolder(it) }
    }
}
