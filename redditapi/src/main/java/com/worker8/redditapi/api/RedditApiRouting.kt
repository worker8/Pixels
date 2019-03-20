package com.worker8.redditapi.api

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.core.Deserializable
import com.github.kittinunf.fuel.core.HeaderValues
import com.github.kittinunf.fuel.core.Parameters
import com.github.kittinunf.fuel.rx.rxObject
import com.github.kittinunf.fuel.util.FuelRouting

interface RedditApiRouting : FuelRouting {

    override val basePath: String
        get() = "https://www.reddit.com/"

    override val params: Parameters?
        get() = null

    override val body: String?
        get() = null

    override val bytes: ByteArray?
        get() = null

    override val headers: Map<String, HeaderValues>?
        get() = null

    fun <T : Any> call(deserializable: Deserializable<T>) =
        Fuel.request(this).rxObject(deserializable)
}
