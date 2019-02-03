package com.worker8.redditapi.model.t1_comment.response

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.RedditApi
import com.worker8.redditapi.model.base.RedditObject
import com.worker8.redditapi.model.t1_comment.data.RedditCommentListingData
import java.io.Reader

data class RedditCommentListingObject(
    @SerializedName("data")
    override val value: RedditCommentListingData = RedditCommentListingData(),
    @SerializedName("kind")
    override val kind: String = "" // kind = "Listing"
) : RedditObject<RedditCommentListingData> {
    class Deserializer : ResponseDeserializable<RedditCommentListingObject> {
        override fun deserialize(reader: Reader) = RedditApi.gson.fromJson(reader, RedditCommentListingObject::class.java)
    }
}
