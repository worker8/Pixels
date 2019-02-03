package com.worker8.redditapi.model.poko

data class Oembed(val width: Int = 0,
                  val height: Int = 0,
                  val html: String = "",
                  val provider_name: String = "",
                  val provider_url: String = "",
                  val thumbnail_url: String = "",
                  val type: String = "",
                  val url: String = "",
                  val version: String = ""
)
