package com.worker8.redditapi

import com.google.gson.annotations.SerializedName

data class ListingData(
    val after: String = "",
    val before: String = "",
    @SerializedName("children")
    val valueList: List<RedditLink> = listOf(),
    val dist: Int = 0,
    val modhash: String = ""
) {
    fun getRedditImageLinks() = valueList.filter { it.value.url.isImageUrl() }
}
