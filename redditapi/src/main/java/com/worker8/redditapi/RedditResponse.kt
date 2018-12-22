package com.worker8.redditapi

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.annotations.SerializedName
import java.io.Reader

data class RedditResponse<T>(
        @SerializedName("data")
        val value: T,
        val kind: String
) {
    class Deserializer<T> : ResponseDeserializable<RedditResponse<T>> {
        override fun deserialize(reader: Reader): RedditResponse<T> = RedditApi2.gson.fromJson(reader, RedditResponse::class.java) as RedditResponse<T>
    }
}