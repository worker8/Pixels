package com.worker8.redditapi.model.listing

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.t1_comment.RedditCommentObject

interface RedditObject<T> {
    val value: T
    val kind: String
}

class RedditListing(
    val after: String,
    val before: String,
    val valueList: List<RedditReply>,
    val dist: Int,
    val modhash: String
)

sealed class RedditReply {
    class T1_RedditObject(@SerializedName("data") val value: RedditCommentObject, @SerializedName("kind") val kind: String) : RedditReply()
    class TM_RedditObject(@SerializedName("data") val value: TMore, @SerializedName("kind") val kind: String) : RedditReply()
}

class TMoreListing(
    val after: String = "",
    val before: String = "",
    @SerializedName("children")
    val valueList: List<TMore> = listOf(),
    val dist: Int = 0,
    val modhash: String = ""
)

data class TMore(
    val children: List<String>,
    val count: Int,
    val depth: Int,
    val id: String,
    val name: String,
    val parent_id: String
)


