package beepbeep.pixelsforreddit.home

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage

class HomeAdapter : PagedListAdapter<GalleryImage, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val galleryImage = getItem(position)
        galleryImage?.also { (holder as HomeViewHolder).bind(it) }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<GalleryImage>() {
            override fun areContentsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: GalleryImage, newItem: GalleryImage): Boolean =
                    oldItem.id == newItem.id

            override fun getChangePayload(oldItem: GalleryImage, newItem: GalleryImage): Any? =
                    null
        }
    }
}