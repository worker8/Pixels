package beepbeep.pixelsforredditx.ui.comment.viewholder

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import beepbeep.pixelsforredditx.R
import beepbeep.pixelsforredditx.extension.toRelativeTimeString

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import kotlinx.android.synthetic.main.comment_header.view.*

class CommentHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(headerData: RedditLinkListingData) {
        val redditLinkData = headerData.valueList[0].value
        itemView.apply {
            val cropOptions = RequestOptions()
                .fitCenter()
                .placeholder(ColorDrawable(0xaaAAaa))
            Glide.with(context)
                .load(redditLinkData.url)
                .apply(cropOptions)
                .into(commentHeaderImage)

            commentHeaderAuthor.text = redditLinkData.author
            commentHeaderTitle.text = redditLinkData.title
            commentHeaderDateTime.text = redditLinkData.created.toRelativeTimeString()

            commentPoint.text = redditLinkData.score.toString()
            commentCount.text = redditLinkData.num_comments.toString()
        }
    }

    companion object {
        fun create(parent: ViewGroup) =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.comment_header, parent, false)
                .let { CommentHeaderViewHolder(it) }
    }
}
