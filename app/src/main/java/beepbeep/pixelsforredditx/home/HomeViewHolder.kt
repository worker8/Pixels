package beepbeep.pixelsforredditx.home

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.worker8.redditapi.model.t3_link.RedditLinkObject
import kotlinx.android.synthetic.main.home_item.view.*

class HomeViewHolder(itemView: View, val callback: (commentId: String) -> Unit) : RecyclerView.ViewHolder(itemView) {
    fun bind(redditLink: RedditLinkObject) {
        itemView.apply {
            context?.also { _context ->
                val cropOptions = RequestOptions()
                    .fitCenter()
                    .placeholder(ColorDrawable(0xaaAAaa))
                Glide.with(_context)
                    .load(redditLink.value.url)
                    .apply(cropOptions)
                    .into(homeItemImage)
            }
            redditLink.value.apply {
                homeItemTitle.text = title
                homeItemUsername.text = author
                homeItemDateTime.text = created.toRelativeTimeString()
                itemView.setOnClickListener { callback(id) }
                Log.d("ddw", "commentId: ${this.id}")
            }

        }
    }

    companion object {
        fun create(parent: ViewGroup, callback: (commentId: String) -> Unit) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item, parent, false)
                .let { HomeViewHolder(it, callback) }
    }
}
