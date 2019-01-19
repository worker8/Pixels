package com.worker8.redditapi.model.t1_comment

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.listing.RedditObject

data class RedditCommentObject(
    @SerializedName("data")
    override val value: RedditCommentData = RedditCommentData(),
    @SerializedName("kind")
    override val kind: String = "" // kind = t1, Comment
) : RedditObject<RedditCommentData>()
