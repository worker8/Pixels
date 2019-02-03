package com.worker8.redditapi.model.t1_comment.data

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject

data class RedditReplyListingData(
    val after: String = "",
    val before: String = "",
    @SerializedName("children")
    val valueList: List<RedditReplyDynamicObject> = listOf(),
    val dist: Int = 0,
    val modhash: String = ""
)
