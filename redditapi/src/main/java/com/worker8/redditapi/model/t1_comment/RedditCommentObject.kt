package com.worker8.redditapi.model.t1_comment

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.listing.RedditCommentDataType
import com.worker8.redditapi.model.listing.RedditObject

data class RedditCommentObject(
    @SerializedName("data")
    override val value: RedditCommentDataType.RedditCommentData = RedditCommentDataType.RedditCommentData(),
    @SerializedName("kind")
    override val kind: String = "" // kind = t1, Comment
) : RedditObject<RedditCommentDataType.RedditCommentData>
