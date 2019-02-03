package com.worker8.redditapi.model.t3_link.response

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.base.RedditObject
import com.worker8.redditapi.model.t3_link.data.RedditLinkData

data class RedditLinkObject(
    @SerializedName("data")
    override val value: RedditLinkData = RedditLinkData(),
    @SerializedName("kind")
    override val kind: String = "" // kind = t3, Link
) : RedditObject<RedditLinkData>
