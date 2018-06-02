package beepbeep.pixelsforreddit.home

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import beepbeep.pixelsforreddit.common.HeaderFooterPagedListAdapter
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage

class HomeAdapter : HeaderFooterPagedListAdapter<GalleryImage>(POST_COMPARATOR) {
    override var hasFooter = false

    override fun getRealItemViewType(position: Int) = 0

    override fun onCreateRealViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder.create(parent)
    }

    override fun onBindRealViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val galleryImage = getItem(position)
        galleryImage?.also { (holder as HomeViewHolder).bind(it) }
    }

    override fun onCreateFooterViewHolder(parent: ViewGroup): RecyclerView.ViewHolder? {
        return LoadingFooterViewHolder.create(parent)
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