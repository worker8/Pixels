package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.comment_header.view.*

class CommentHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(imageUrl: String) {
        itemView.apply {
            val cropOptions = RequestOptions()
                .fitCenter()
                .placeholder(ColorDrawable(0xaaAAaa))
            Glide.with(context)
                .load(imageUrl)
                .apply(cropOptions)
                .into(commentImage)
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_header, parent, false)
                .let { CommentHeaderViewHolder(it) }
    }
}
