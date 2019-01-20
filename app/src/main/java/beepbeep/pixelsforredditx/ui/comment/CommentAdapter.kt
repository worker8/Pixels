package beepbeep.pixelsforredditx.ui.comment

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.extension.ofType
import io.reactivex.subjects.PublishSubject

class CommentAdapter : ListAdapter<CommentAdapter.CommentViewType, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private val postClickedSubject: PublishSubject<String> = PublishSubject.create()
    val postClickedObservable = postClickedSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val item = getItem(viewType)
        item.ofType<CommentViewType.Header> {
            return CommentHeaderViewHolder.create(parent)
        }
        return CommentViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        item.ofType<CommentViewType.Item> {
            (holder as CommentViewHolder).bind(it.itemData)
        }

        item.ofType<CommentViewType.Header> {
            (holder as CommentHeaderViewHolder).bind(it.headerData)
        }
    }

    override fun getItemViewType(position: Int) = position

    sealed class CommentViewType() {
        class Header(val headerData: String) : CommentViewType()
        class Item(val itemData: Pair<Int, String>) : CommentViewType()
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<CommentAdapter.CommentViewType>() {
            override fun areItemsTheSame(oldItem: CommentViewType, newItem: CommentViewType): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CommentViewType, newItem: CommentViewType): Boolean {
                oldItem.ofType<CommentViewType.Item> { _oldItem ->
                    newItem.ofType<CommentViewType.Item> { _newItem ->
                        val (oldLevel, oldComment) = _oldItem.itemData
                        val (newLevel, newComment) = _newItem.itemData
                        return oldLevel == newLevel && oldComment == newComment
                    }
                }

                oldItem.ofType<CommentViewType.Header> { _oldItem ->
                    newItem.ofType<CommentViewType.Header> { _newItem ->
                        return _oldItem.headerData == _newItem.headerData
                    }
                }
                return false // this means they have different type
            }
        }
    }
}
