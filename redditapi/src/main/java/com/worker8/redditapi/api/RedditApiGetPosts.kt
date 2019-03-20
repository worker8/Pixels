package com.worker8.redditapi.api

import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Method.GET
import com.github.kittinunf.fuel.core.Parameters
import com.worker8.redditapi.model.t3_link.deserializer.RedditLinkListingObjectDeserializer

class RedditApiGetPosts(val subreddit: String, pageToken: String = "") : RedditApiRouting {

    override val method: Method = GET

    override val path: String = "r/$subreddit.json"

    override val params: Parameters? = listOf("after" to pageToken)

    fun call() = call(RedditLinkListingObjectDeserializer())
}
