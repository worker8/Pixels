package com.worker8.redditapi.model.listing

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.Gildings
import com.worker8.redditapi.model.t1_comment.RedditReplyListingObject

interface RedditObject<T> {
    val value: T
    val kind: String
}


