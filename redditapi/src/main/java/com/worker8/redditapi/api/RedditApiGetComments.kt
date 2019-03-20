package com.worker8.redditapi.api

import com.github.kittinunf.fuel.core.Method
import com.github.kittinunf.fuel.core.Method.GET
import com.worker8.redditapi.model.t1_comment.deserializer.RedditCommentDeserializer

class RedditApiGetComments(commentId: String) : RedditApiRouting {

    override val method: Method = GET

    override val path: String = "comments/$commentId.json"

    fun call() = call(RedditCommentDeserializer())
}
