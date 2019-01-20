package com.worker8.redditapi.model.t1_comment

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.*
import com.worker8.redditapi.model.t3_link.RedditLinkListingData
import com.worker8.redditapi.model.t3_link.RedditLinkListingObject
import java.io.Reader
import java.lang.reflect.Type

class RedditCommentDeserializer : ResponseDeserializable<Pair<RedditLinkListingData, RedditCommentListingData>> {
    override fun deserialize(reader: Reader): Pair<RedditLinkListingData, RedditCommentListingData> {
        val jsonParser = JsonParser()
        val jsonArray = jsonParser.parse(reader).asJsonArray

        val gson = GsonBuilder().registerTypeAdapter(RedditCommentListingObject::class.java, RedditCommentListingObjectDeserializer()).create()
        val titleListing = gson.fromJson(jsonArray[0], RedditLinkListingObject::class.java)
        val commentTreeListing = gson.fromJson(jsonArray[1], RedditCommentListingObject::class.java)

        traverse(commentTreeListing, 0)

        return titleListing.value to commentTreeListing.value
    }

    fun traverse(rootNode: RedditCommentListingObject, level: Int) {
        if (rootNode.value.valueList.isEmpty()) {
            return
        }
        val spaces = "  ".repeat(level)
        rootNode.value.valueList.forEach {
            Log.d("ddw", "${spaces}>>body: ${it.value.body}")
            traverse(it.value.replies, level + 1)
        }
    }
}

// reddit api can sometimes reply "" or the object itself, this deserializer handles this polymorphic object
class RedditCommentListingObjectDeserializer : JsonDeserializer<RedditCommentListingObject> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditCommentListingObject {
        val newJsonString = json.toString().replace("\"replies\":\"\"", "\"replies\":{}")
//        val jsonObject = (json as JsonObject)
//        jsonObject.getAsJsonObject("")
//        if ((json as JsonObject).size() == 0) {
//            return RedditCommentListingObject()
//        } else {
        return Gson().fromJson(newJsonString, RedditCommentListingObject::class.java)
//        }
//        try {
//            val gson = GsonBuilder().registerTypeAdapter(RedditCommentListingObject::class.java, RedditCommentListingObjectDeserializer()).create()
//            return gson.fromJson(json, RedditCommentListingObject::class.java)
//        } catch (e: JsonSyntaxException) {
        // this is expected since when the replies is empty, swallow error
//            return RedditCommentListingObject()
//        }
    }

}
