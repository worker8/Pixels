package com.worker8.redditapi

data class MediaEmbed(
    val width: Int = 0,
    val height: Int = 0,
    val content: String = "",
    val scrolling: Boolean = false
)
