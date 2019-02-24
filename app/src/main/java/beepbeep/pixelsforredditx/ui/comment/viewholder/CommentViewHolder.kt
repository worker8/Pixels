package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.graphics.Color
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.ui.comment.CommentAdapter
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(commentRow: CommentAdapter.CommentViewType.Item, position: Int) {
        itemView.apply {
            val color = rainbowColorArray[position % rainbowColorArray.size]
            commentIndentationBar.setBackgroundColor(color)
            commentIndentationBar.visibility = if (commentRow.level.isEmpty()) View.GONE else View.VISIBLE
            commentIndentation.text = commentRow.level
            commentItemText.text = commentRow.commentHtmlString
            commentItemText.movementMethod = LinkMovementMethod.getInstance()
            commentItemConcatInfo.text = commentRow.concatenatedInfoString
        }
    }

    companion object {
        val rainbowColorArray = listOf(
            Color.rgb(148, 0, 211), // Violet
            Color.rgb(75, 0, 130), // Indigo
            Color.rgb(0, 0, 255), // Blue
            Color.rgb(0, 255, 0), // Green
            Color.rgb(121, 85, 72),
            Color.rgb(255, 127, 0), // Orange
            Color.rgb(255, 9, 0)) // Red

        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_item, parent, false)
                .let { CommentViewHolder(it) }
    }
}
