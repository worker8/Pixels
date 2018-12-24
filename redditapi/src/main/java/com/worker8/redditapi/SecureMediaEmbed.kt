package com.worker8.redditapi

data class SecureMediaEmbed(
    val width: Int = 0,
    val height: Int = 0,
    val content: String = "",
    val media_domain_url: String = "",
    val scrolling: Boolean = false
)
