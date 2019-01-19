package com.worker8.redditapi.model.listing

interface RedditObject<T> {
    val value: T
    val kind: String
}
