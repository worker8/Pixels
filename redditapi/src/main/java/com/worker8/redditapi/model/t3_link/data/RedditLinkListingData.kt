package com.worker8.redditapi.model.t3_link.data

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.common.isImageUrl
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject

data class RedditLinkListingData(
    val after: String = "",
    val before: String = "",
    @SerializedName("children")
    val valueList: List<RedditLinkObject> = listOf(),
    val dist: Int = 0,
    val modhash: String = ""
) {
    fun getRedditImageLinks() = valueList.filter { it.value.url.isImageUrl() }
}
