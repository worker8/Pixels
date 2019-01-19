package com.worker8.redditapi.model.t1_comment

import android.util.Log
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.worker8.redditapi.model.t3_link.RedditLinkData
import com.worker8.redditapi.model.t3_link.RedditLinkListingObject
import java.io.Reader

class RedditCommentDeserializer : ResponseDeserializable<Pair<RedditLinkData, RedditCommentData>> {
    override fun deserialize(reader: Reader): Pair<RedditLinkData, RedditCommentData>? {
        val jsonParser = JsonParser()
        val jsonArray = jsonParser.parse(reader).asJsonArray

        Log.d("ddw", "jsonArray[0]: ${jsonArray[0]}")
        Log.d("ddw", "jsonArray[1]: ${jsonArray[1]}")


        val gson = Gson()
        val res0 = gson.fromJson(jsonArray[0], RedditLinkListingObject::class.java)
        val res1 = gson.fromJson(jsonArray[1], RedditCommentListingObject::class.java)

        Log.d("ddw", "res0: ${res0.value}")
        res1.value.valueList.forEach {
            Log.d("ddw", "res1: ${it.value.body}")
        }

        return super.deserialize(reader)
    }
}
