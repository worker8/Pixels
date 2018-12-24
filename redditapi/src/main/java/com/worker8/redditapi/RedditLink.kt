package com.worker8.redditapi

import com.google.gson.annotations.SerializedName

data class RedditLink(
        @SerializedName("data")
        val value: RedditLinkData = RedditLinkData(),
        val kind: String = ""
)
