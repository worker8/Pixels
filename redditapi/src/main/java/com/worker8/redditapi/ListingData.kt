package com.worker8.redditapi

import com.google.gson.annotations.SerializedName

data class ListingData(
    val after: String,
    val before: Any,
    @SerializedName("children")
    val valueList: List<RedditLink>,
    val dist: Int,
    val modhash: String
) {
    fun getRedditImageLinks() = valueList.filter { it.value.url.isImageUrl() }
}
