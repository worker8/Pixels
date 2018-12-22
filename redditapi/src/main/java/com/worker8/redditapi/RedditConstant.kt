package com.worker8.redditapi

import net.dean.jraw.http.UserAgent
import net.dean.jraw.http.oauth.Credentials
import java.util.*

val userAgent = UserAgent.of("android", "com.worker8.redditapi", "v0.1", "momodao")
val credentials = Credentials.userlessApp("WckNZW7vUxT3-g", UUID.randomUUID())