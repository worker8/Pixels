package com.worker8.redditapi.model.t3_link.response

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.base.RedditObject
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData

data class RedditLinkListingObject(
    @SerializedName("data")
    override val value: RedditLinkListingData,
    @SerializedName("kind")
    override val kind: String = "" // kind = "Listing"
) : RedditObject<RedditLinkListingData>
