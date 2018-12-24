package beepbeep.pixelsforreddit.home

import android.animation.ObjectAnimator
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.pixelsforreddit.R
import com.bumptech.glide.GenericTransitionOptions
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.ViewPropertyTransition
import com.worker8.redditapi.RedditLink
import kotlinx.android.synthetic.main.home_item.view.*

class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(redditLink: RedditLink) {
        itemView.apply {
            context?.also { _context ->
                val animationObject = ViewPropertyTransition.Animator { view ->
                    view.alpha = 0f
                    val fadeAnim = ObjectAnimator.ofFloat(view, "alpha", 0f, 1f)
                    fadeAnim.duration = 500
                    fadeAnim.start()
                }
                val cropOptions = RequestOptions()
                    .fitCenter()
                    .placeholder(ColorDrawable(0xaaAAaa))

                Log.d("ddw", "redditLink.url: ${redditLink.value.url}")
                Glide.with(_context)
                    .load(redditLink.value.url)
                    .transition(GenericTransitionOptions.with(animationObject))
                    .apply(cropOptions)
                    .into(homeItemImage)
            }
            homeItemTitle.text = redditLink.value.title
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.home_item, parent, false)
                .let { HomeViewHolder(it) }
    }
}
