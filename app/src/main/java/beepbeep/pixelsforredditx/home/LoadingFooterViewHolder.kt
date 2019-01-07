package beepbeep.pixelsforredditx.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.pixelsforredditx.R


class LoadingFooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.loading_footer_item, parent, false)
                .let { LoadingFooterViewHolder(it) }
    }
}
