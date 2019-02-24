package com.worker8.redditapi.model.t1_comment.deserializer

import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.worker8.redditapi.RedditApi.Companion.gson
import com.worker8.redditapi.model.t1_comment.data.RedditCommentDynamicData
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject
import com.worker8.redditapi.model.t1_comment.response.RedditReplyListingObject
import java.lang.reflect.Type

class RedditReplyListingDataDeserializer : JsonDeserializer<RedditReplyListingData> {

    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): RedditReplyListingData {
        val jsonParser = JsonParser()
        val jsonObject = jsonParser.parse(json.toString()).asJsonObject
        val jsonArray = jsonObject.get("children").asJsonArray
        val result = jsonArray.map {
            var redditReply: RedditReplyDynamicObject
            if (it.asJsonObject.get("kind").asString == "t1") {
                try {
                    val value = it.asJsonObject.get("data")

                    val author1 = value.asJsonObject.get("author").asString
                    val createdUtc1 = value.asJsonObject.get("created_utc").asInt
                    val created1 = value.asJsonObject.get("created").asInt
                    val body1 = value.asJsonObject.get("body").asString
                    val bodyHtml1 = value.asJsonObject.get("body_html").asString
                    val score1 = value.asJsonObject.get("score").asInt

                    val replies1 = try {
                        gson.fromJson(value.asJsonObject.get("replies"), RedditReplyListingObject::class.java)
                    } catch (e: Exception) {
                        RedditReplyListingObject()
                    }

                    val redditCommentData = RedditCommentDynamicData.T1RedditCommentData(
                        author = author1,
                        created_utc = createdUtc1,
                        body = body1,
                        body_html = bodyHtml1,
                        created = created1,
                        replies = replies1,
                        score = score1
                    )

                    redditReply = RedditReplyDynamicObject.T1RedditObject(value = redditCommentData, kind = it.asJsonObject.get("kind").asString)
                } catch (e: JsonSyntaxException) {
                    redditReply = RedditReplyDynamicObject.T1RedditObject(value = RedditCommentDynamicData.T1RedditCommentData(), kind = "t1")
                    e.printStackTrace()

                    Log.d("ddw", "e.message: !! ${e.message}")
                    Log.d("ddw", "ERROR: !! $it")
                }
            } else {
                redditReply = gson.fromJson(it.asJsonObject, RedditReplyDynamicObject.TMRedditObject::class.java)
            }
            redditReply
        }
        val before = if (jsonObject.get("before").isJsonNull) "" else jsonObject.get("before").asString
        val after = if (jsonObject.get("after").isJsonNull) "" else jsonObject.get("after").asString
        val dist = if (jsonObject.get("dist").isJsonNull) 0 else jsonObject.get("dist").asInt
        val modhash = if (jsonObject.get("modhash").isJsonNull) "" else jsonObject.get("modhash").asString
        return RedditReplyListingData(
            before = before,
            after = after,
            valueList = result,
            dist = dist,
            modhash = modhash)
    }
}
