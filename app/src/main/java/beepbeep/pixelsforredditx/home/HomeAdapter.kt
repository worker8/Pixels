package beepbeep.pixelsforredditx.home

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import io.reactivex.subjects.PublishSubject

class HomeAdapter : ListAdapter<RedditLinkObject, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private val postClickedSubject: PublishSubject<String> = PublishSubject.create()
    val postClickedObservable = postClickedSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder.create(parent) { commentId -> postClickedSubject.onNext(commentId) }
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
