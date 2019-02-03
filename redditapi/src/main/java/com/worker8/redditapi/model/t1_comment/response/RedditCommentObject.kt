package com.worker8.redditapi.model.t1_comment.response

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.base.RedditObject
import com.worker8.redditapi.model.t1_comment.data.RedditCommentDynamicData

data class RedditCommentObject(
    @SerializedName("data")
    override val value: RedditCommentDynamicData.T1RedditCommentData = RedditCommentDynamicData.T1RedditCommentData(),
    @SerializedName("kind")
    override val kind: String = "" // kind = t1, Comment
) : RedditObject<RedditCommentDynamicData.T1RedditCommentData>
