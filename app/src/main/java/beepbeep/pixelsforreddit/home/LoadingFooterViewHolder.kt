package beepbeep.pixelsforreddit.home

import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.pixelsforreddit.R
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.home_item.view.*


class LoadingFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup) =
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.loading_footer_item, parent, false)
                        .let { LoadingFooterViewHolder(it) }
    }
}