package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import com.worker8.redditapi.model.t1_comment.data.RedditCommentDynamicData
import kotlinx.android.synthetic.main.comment_item.view.*
import kotlinx.android.synthetic.main.comment_more_item.view.*

class CommentMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(commentPair: Pair<Int, RedditCommentDynamicData.TMore>, position: Int) {
        val (level, redditCommentDataType) = commentPair
        itemView.apply {
            val color = CommentViewHolder.rainbowColorArray[position % CommentViewHolder.rainbowColorArray.size]
            commentMoreIndentationBar.setBackgroundColor(color)
            commentMoreIndentation.text = " ".repeat(level)
            commentMoreCountText.text = "(${redditCommentDataType.count})"
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_more_item, parent, false)
                .let { CommentMoreViewHolder(it) }
    }
}
