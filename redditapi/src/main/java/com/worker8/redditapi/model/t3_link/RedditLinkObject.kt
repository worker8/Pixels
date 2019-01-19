package com.worker8.redditapi.model.t3_link

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.listing.RedditObject

data class RedditLinkObject(
    @SerializedName("data")
    override val value: RedditLinkData = RedditLinkData(),

    @SerializedName("kind")
    override val kind: String = "" // kind = t3, Link
) : RedditObject<RedditLinkData>()
