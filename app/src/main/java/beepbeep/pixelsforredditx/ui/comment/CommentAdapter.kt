package beepbeep.pixelsforredditx.ui.comment

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentEmptyViewHolder
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentHeaderViewHolder
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentMoreViewHolder
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentViewHolder
import com.worker8.redditapi.model.t1_comment.data.RedditCommentDynamicData
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import io.reactivex.subjects.PublishSubject

class CommentAdapter : ListAdapter<CommentAdapter.CommentViewType, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private val postClickedSubject: PublishSubject<String> = PublishSubject.create()

    init {
        hasStableIds()
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                CommentHeaderViewHolder.create(parent)
            }
            1 -> {
                CommentViewHolder.create(parent)
            }
            2 -> {
                CommentMoreViewHolder.create(parent)
            }
            else -> {
                CommentEmptyViewHolder.create(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (item.typeNumber) {
            0 -> (holder as CommentHeaderViewHolder).bind((item as CommentViewType.Header).headerData)
            1 -> (holder as CommentViewHolder).bind((item as CommentViewType.Item), position)
            2 -> (holder as CommentMoreViewHolder).bind((item as CommentViewType.ItemViewMore).itemMoreData, position)
            else -> {
            }
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).typeNumber

    sealed class CommentViewType(open val id: String, val typeNumber: Int) {
        class Header(val headerData: RedditLinkListingData) : CommentViewType(id = "header_type", typeNumber = 0)
        class Item(override val id: String, val level: String, val concatenatedInfoString: CharSequence, val commentHtmlString: CharSequence) : CommentViewType(id = id, typeNumber = 1)
        class ItemViewMore(override val id: String, val itemMoreData: Pair<Int, RedditCommentDynamicData.TMore>) : CommentViewType(id = id, typeNumber = 2)
        class Empty : CommentViewType(id = "empty_type", typeNumber = 3)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CommentAdapter.CommentViewType>() {
            override fun areItemsTheSame(oldItem: CommentViewType, newItem: CommentViewType): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CommentViewType, newItem: CommentViewType): Boolean {
                return oldItem.id == newItem.id // assume content doesn't get edited (not supported)
            }
        }
    }
}
