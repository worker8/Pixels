package beepbeep.pixelsforredditx.ui.comment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import kotlinx.android.synthetic.main.comment_item.view.*

class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(commentPair: Pair<Int, String>) {
        val (level, comment) = commentPair
        itemView.apply {
            //commentIndentation.scaleX = 1 + (level * 0.1f)
            commentGuideline.setGuidelinePercent(level * 0.02f)
            commentItemText.text = comment
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_item, parent, false)
                .let { CommentViewHolder(it) }
    }
}
