package com.worker8.redditapi.model.listing

abstract class RedditObject<T> {
    abstract val value: T
    abstract val kind: String
}
