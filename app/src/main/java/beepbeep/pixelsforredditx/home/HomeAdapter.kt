package beepbeep.pixelsforredditx.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worker8.redditapi.model.t3_link.RedditLinkObject

class HomeAdapter : ListAdapter<RedditLinkObject, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditLinkObject>() {
            override fun areItemsTheSame(oldItem: RedditLinkObject, newItem: RedditLinkObject): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RedditLinkObject, newItem: RedditLinkObject): Boolean {
                return oldItem == newItem
            }
        }
    }
}
