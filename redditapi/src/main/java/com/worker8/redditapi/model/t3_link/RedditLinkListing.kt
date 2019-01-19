package com.worker8.redditapi.model.t3_link

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.RedditApi
import java.io.Reader

data class RedditLinkListing(
    @SerializedName("data")
    val value: RedditLinkListingData = RedditLinkListingData(),
    @SerializedName("kind")
    val kind: String = ""
) {
    class Deserializer : ResponseDeserializable<RedditLinkListing> {
        override fun deserialize(reader: Reader) = RedditApi.gson.fromJson(reader, RedditLinkListing::class.java)
    }
}
