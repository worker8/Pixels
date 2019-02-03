package com.worker8.redditapi.model.poko

data class Image(
    val id: String,
    val resolutions: List<Resolution>,
    val source: Source
)
