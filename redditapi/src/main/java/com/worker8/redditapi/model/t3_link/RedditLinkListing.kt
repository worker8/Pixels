package com.worker8.redditapi.model.t3_link

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.listing.RedditObjectListing
import java.io.Reader

data class RedditLinkListing(@SerializedName("data") override val value: RedditLinkListingData, @SerializedName("kind") override val kind: String = "") : RedditObjectListing<RedditLinkListingData>() {
    class Deserializer : ResponseDeserializable<RedditLinkListing> {
        override fun deserialize(reader: Reader) = RedditApi.gson.fromJson(reader, RedditLinkListing::class.java)
    }
}
