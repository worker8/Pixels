package com.worker8.redditapi

data class Media(
    val oembed: Oembed = Oembed(),
    val type: String = ""
)
