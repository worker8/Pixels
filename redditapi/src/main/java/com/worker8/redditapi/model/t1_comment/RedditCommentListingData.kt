package com.worker8.redditapi.model.t1_comment

import com.google.gson.annotations.SerializedName

data class RedditCommentListingData(
    val after: String = "",
    val before: String = "",
    @SerializedName("children")
    val valueList: List<RedditCommentObject> = listOf(),
    val dist: Int = 0,
    val modhash: String = ""
)
