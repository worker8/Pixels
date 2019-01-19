package com.worker8.redditapi.model.listing

abstract class RedditObjectListing<T> {
    abstract val value: T
    abstract val kind: String
}
