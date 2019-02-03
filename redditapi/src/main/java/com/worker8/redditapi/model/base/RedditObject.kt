package com.worker8.redditapi.model.base

interface RedditObject<T> {
    val value: T
    val kind: String
}


