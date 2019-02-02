package beepbeep.pixelsforredditx.ui.comment

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.extension.ofType
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentEmptyViewHolder
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentHeaderViewHolder
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentMoreViewHolder
import beepbeep.pixelsforredditx.ui.comment.viewholder.CommentViewHolder
import com.worker8.redditapi.model.listing.RedditCommentDataType

import com.worker8.redditapi.model.t3_link.RedditLinkListingData
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class CommentAdapter : ListAdapter<CommentAdapter.CommentViewType, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private val postClickedSubject: PublishSubject<String> = PublishSubject.create()
    val postClickedObservable: Observable<String> = postClickedSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = getItem(viewType)
        item.ofType<CommentViewType.Header> {
            return CommentHeaderViewHolder.create(parent)
        }
        item.ofType<CommentViewType.Empty> {
            return CommentEmptyViewHolder.create(parent)
        }
        item.ofType<CommentViewType.ItemViewMore> {
            return CommentMoreViewHolder.create(parent)
        }
        return CommentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item.ofType<CommentViewType.Item> {
            (holder as CommentViewHolder).bind(it)
        }

        item.ofType<CommentViewType.Header> {
            (holder as CommentHeaderViewHolder).bind(it.headerData)
        }

        item.ofType<CommentViewType.ItemViewMore> {
            (holder as CommentMoreViewHolder).bind(it.itemMoreData)
        }
    }

    override fun getItemViewType(position: Int) = position

    sealed class CommentViewType() {
        class Header(val headerData: RedditLinkListingData) : CommentViewType()
        class Item(val level: String, val concatenatedInfoString: String, val commentHtmlString: String) : CommentViewType()
        class ItemViewMore(val itemMoreData: Pair<Int, RedditCommentDataType.TMore>) : CommentViewType()
        class Empty() : CommentViewType()
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CommentAdapter.CommentViewType>() {
            override fun areItemsTheSame(oldItem: CommentViewType, newItem: CommentViewType): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CommentViewType, newItem: CommentViewType): Boolean {
                oldItem.ofType<CommentViewType.Item> { _oldItem ->
                    newItem.ofType<CommentViewType.Item> { _newItem ->
                        return _oldItem == _newItem
                    }
                }

                oldItem.ofType<CommentViewType.Header> { _oldItem ->
                    newItem.ofType<CommentViewType.Header> { _newItem ->
                        return _oldItem.headerData == _newItem.headerData
                    }
                }

                oldItem.ofType<CommentViewType.ItemViewMore> { _oldItem ->
                    newItem.ofType<CommentViewType.ItemViewMore> { _newItem ->
                        val (oldLevel, oldComment) = _oldItem.itemMoreData
                        val (newLevel, newComment) = _newItem.itemMoreData
                        return oldLevel == newLevel && oldComment == newComment
                    }
                }

                oldItem.ofType<CommentViewType.Empty> { _oldItem ->
                    newItem.ofType<CommentViewType.Empty> { _newItem ->
                        return _oldItem == _newItem
                    }
                }
                return false // this means they have different type
            }
        }
    }
}
