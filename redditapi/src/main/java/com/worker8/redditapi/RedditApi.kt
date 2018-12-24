package com.worker8.redditapi

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import io.reactivex.Observable

class RedditApi(val subreddit: String = "pics") {
    val REDDIT_API_BASE = "https://www.reddit.com/r/"
    var after = ""
    fun getMorePosts(): Observable<Result<Listing, FuelError>> =
        "$REDDIT_API_BASE$subreddit.json?after=$after"
            .httpGet()
            .rx_object(Listing.Deserializer())
            .toObservable()
            .doOnNext { (listing, fuelError) ->
                listing?.value?.after?.also { after = it }
            }

    companion object {
        val gson = GsonBuilder().create()
    }
}
