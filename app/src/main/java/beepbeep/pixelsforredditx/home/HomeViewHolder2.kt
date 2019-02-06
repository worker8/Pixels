package beepbeep.pixelsforredditx.home

import android.graphics.drawable.ColorDrawable
import android.system.Os.bind
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.common.epoxy.BaseEpoxyHolder
import beepbeep.pixelsforredditx.common.epoxy.KotlinEpoxyHolder
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject

@EpoxyModelClass(layout = R.layout.home_item)
abstract class HomeViewHolder2 : EpoxyModelWithHolder<HomeViewHolder2.Holder>() {

    @EpoxyAttribute
    lateinit var redditLink: RedditLinkObject
    @EpoxyAttribute
    lateinit var callback: (commentId: String) -> Unit

    override fun bind(holder: Holder) {
        holder.apply {
            val cropOptions = RequestOptions()
                .fitCenter()
                .placeholder(ColorDrawable(0xaaAAaa))
            Glide.with(homeItemContainer.context)
                .load(redditLink.value.url)
                .apply(cropOptions)
                .into(homeItemImage)

            redditLink.value.apply {
                homeItemTitle.text = title
                homeItemUsername.text = author
                homeItemScore.text = "${score} points · "
                homeItemDateTime.text = created.toRelativeTimeString()
                homeItemContainer.setOnClickListener { callback(id) }
            }
        }
    }

    class Holder : KotlinEpoxyHolder() {
        val homeItemContainer by bind<ViewGroup>(R.id.homeItemContainer)
        val homeItemTitle by bind<TextView>(R.id.homeItemTitle)
        val homeItemUsername by bind<TextView>(R.id.homeItemUsername)
        val homeItemScore by bind<TextView>(R.id.homeItemScore)
        val homeItemDateTime by bind<TextView>(R.id.homeItemDateTime)
        val homeItemImage by bind<ImageView>(R.id.homeItemImage)
    }
}
