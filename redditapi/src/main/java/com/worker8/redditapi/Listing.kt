package com.worker8.redditapi

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.annotations.SerializedName
import java.io.Reader

data class Listing(
        @SerializedName("data")
        val value: ListingData,
        val kind: String
){
    class Deserializer : ResponseDeserializable<Listing> {
        override fun deserialize(reader: Reader) = RedditApi2.gson.fromJson(reader, Listing::class.java)
    }
}