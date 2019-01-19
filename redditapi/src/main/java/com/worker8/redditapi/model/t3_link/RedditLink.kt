package com.worker8.redditapi.model.t3_link

import com.google.gson.annotations.SerializedName

data class RedditLink(
    @SerializedName("data")
    val value: RedditLinkData = RedditLinkData(),

    @SerializedName("kind")
    val kind: String = "" // kind = t3, Link
)
