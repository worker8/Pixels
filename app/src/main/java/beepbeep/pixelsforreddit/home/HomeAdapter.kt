package beepbeep.pixelsforreddit.home

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.system.Os.bind
import android.view.ViewGroup
import com.worker8.redditapi.RedditLink
import net.dean.jraw.models.Submission

class HomeAdapter : ListAdapter<RedditLink, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder2.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HomeViewHolder2).bind(getItem(position))
    }
//    override var hasFooter = false
//
//    override fun getRealItemViewType(position: Int) = 0
//
//    override fun onCreateRealViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
//        return HomeViewHolder.create(parent)
//    }
//
//    override fun onBindRealViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        val submission = getItem(position)
//        Log.d("ddw", "${submission?.title}")
//        //galleryImage?.also { (holder as HomeViewHolder).bind(it) }
//    }
//
//    override fun onCreateFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
//        return LoadingFooterViewHolder.create(parent)
//    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<RedditLink>() {
            override fun areItemsTheSame(oldItem: RedditLink?, newItem: RedditLink?): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RedditLink?, newItem: RedditLink?): Boolean {
                return oldItem == newItem
            }
//            override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
//                    oldItem == newItem
//
//            override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
//                    oldItem.id == newItem.id
//
//            override fun getChangePayload(oldItem: GalleryImage, newItem: GalleryImage): Any? =
//                    null
        }
    }
}