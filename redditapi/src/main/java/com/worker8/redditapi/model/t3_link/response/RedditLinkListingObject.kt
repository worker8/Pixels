package com.worker8.redditapi.model.t3_link.response

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.base.RedditObject
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import java.io.Reader

data class RedditLinkListingObject(
    @SerializedName("data")
    override val value: RedditLinkListingData,
    @SerializedName("kind")
    override val kind: String = "" // kind = "Listing"
) : RedditObject<RedditLinkListingData> {
    class Deserializer : ResponseDeserializable<RedditLinkListingObject> {
        override fun deserialize(reader: Reader): RedditLinkListingObject {
            return RedditApi.gson.fromJson(reader, RedditLinkListingObject::class.java)
        }
    }
}
