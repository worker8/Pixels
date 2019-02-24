package com.worker8.redditapi.model.t1_comment.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.worker8.redditapi.RedditApi.Companion.gson
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData
import com.worker8.redditapi.model.t1_comment.response.RedditCommentListingObject
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject
import com.worker8.redditapi.model.t1_comment.response.RedditReplyListingObject
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import com.worker8.redditapi.model.t3_link.response.RedditLinkListingObject
import java.io.Reader
import java.lang.reflect.Type

class RedditCommentDeserializer : ResponseDeserializable<Pair<RedditLinkListingData, RedditReplyListingData>> {
    override fun deserialize(reader: Reader): Pair<RedditLinkListingData, RedditReplyListingData> {
        val jsonParser = JsonParser()
        val jsonArray = jsonParser.parse(reader).asJsonArray
        val titleListing = gson.fromJson(jsonArray[0], RedditLinkListingObject::class.java)
        val commentTreeListing = gson.fromJson(jsonArray[1], RedditReplyListingObject::class.java)
        return titleListing.value to commentTreeListing.value
    }
}

// reddit api can sometimes reply "" or the object itself, this deserializer handles this polymorphic object
class RedditCommentListingObjectDeserializer : JsonDeserializer<RedditCommentListingObject> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditCommentListingObject {
        val newJsonString = json.toString().replace("\"replies\":\"\"", "\"replies\":{}")
        return Gson().fromJson(newJsonString, RedditCommentListingObject::class.java)
    }
}

class T1RedditObjectDeserializer : JsonDeserializer<RedditReplyDynamicObject> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditReplyDynamicObject {
        val newJsonString = json.toString().replace("\"replies\":\"\"", "\"replies\":{}")
        return Gson().fromJson(newJsonString, RedditReplyDynamicObject::class.java)
    }
}
