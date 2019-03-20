package com.worker8.redditapi.api

import com.google.gson.GsonBuilder
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData
import com.worker8.redditapi.model.t1_comment.deserializer.RedditCommentListingObjectDeserializer
import com.worker8.redditapi.model.t1_comment.deserializer.RedditReplyListingDataDeserializer
import com.worker8.redditapi.model.t1_comment.deserializer.T1RedditObjectDeserializer
import com.worker8.redditapi.model.t1_comment.response.RedditCommentListingObject
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject

object Serializer {

    val gson = GsonBuilder()
        .registerTypeAdapter(RedditCommentListingObject::class.java, RedditCommentListingObjectDeserializer())
        .registerTypeAdapter(RedditReplyDynamicObject.T1RedditObject::class.java, T1RedditObjectDeserializer())
        .registerTypeAdapter(RedditReplyListingData::class.java, RedditReplyListingDataDeserializer())
        .create()
}
