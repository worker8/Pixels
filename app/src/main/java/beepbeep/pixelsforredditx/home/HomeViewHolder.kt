package beepbeep.pixelsforredditx.home

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.worker8.redditapi.model.t3_link.RedditLink
import kotlinx.android.synthetic.main.home_item.view.*

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(redditLink: RedditLink) {
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
            }

        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item, parent, false)
                .let { HomeViewHolder(it) }
    }
}
