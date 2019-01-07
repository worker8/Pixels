package beepbeep.pixelsforredditx.home

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.worker8.redditapi.RedditLink

class HomeAdapter : ListAdapter<RedditLink, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder).bind(getItem(position))
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditLink>() {
            override fun areItemsTheSame(oldItem: RedditLink, newItem: RedditLink): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RedditLink, newItem: RedditLink): Boolean {
                return oldItem == newItem
            }
        }
    }
}
