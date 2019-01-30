package com.worker8.redditapi

import android.util.Log
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rx_object
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import com.worker8.redditapi.model.t1_comment.RedditCommentDeserializer
import com.worker8.redditapi.model.t1_comment.RedditReplyListingData
import com.worker8.redditapi.model.t3_link.RedditLinkListingData
import com.worker8.redditapi.model.t3_link.RedditLinkListingObject
import io.reactivex.Observable

class RedditApi(val subreddit: String = defaultSelectedSubreddit) {

    val REDDIT_API_BASE = "https://www.reddit.com/"
    var after = ""
    fun getMorePosts(): Observable<Result<RedditLinkListingObject, FuelError>> =
        "${REDDIT_API_BASE}r/$subreddit.json?after=$after"
            .httpGet()
            .rx_object(RedditLinkListingObject.Deserializer())
            .toObservable()
            .doOnNext { (listing, fuelError) ->
                listing?.value?.after?.also { after = it }
            }

    fun getComment(commentId: String): Observable<Result<Pair<RedditLinkListingData, RedditReplyListingData>, FuelError>> {
        val curlString = "${REDDIT_API_BASE}comments/${commentId}.json"
            .httpGet()
            .cUrlString()
        Log.d("ddw", "curlString: $curlString")

        return "${REDDIT_API_BASE}comments/${commentId}.json"
            .httpGet()
            .rx_object(RedditCommentDeserializer())
            .toObservable()
    }

    companion object {
        val gson = GsonBuilder().create()
        const val defaultSelectedSubreddit = "PixelArt"
        val subreddits = listOf(
            "pics",
            "aww",
            "art",
            "gifs",
            "SweatyPalms",
            "OldSchoolCool",
            "QuotesPorn",
            "HistoryPorn",
            "funny",
            "PixelArt",
            "drawing",
            "wallpaper+wallpapers",
            "RedditGetsDrawn",
            "ImaginaryMonsters",
            "MicroPorn",
            "MacroPorn",
            "CrappyDesign",
            "ExposurePorn",
            "ITookAPicture",
            "IDAP",
            "illustration",
            "doodles",
            "earthporn",
            "mildlyInteresting",
            "awwnverts",
            "cats",
            "CatPictures",
            "DogPictures",
            "husky+corgi",
            "AnimalPorn",
            "DelightfullyChubby",
            "HardcoreAww",
            "food",
            "FoodPorn",
            "DessertPorn",
            "EarthPorn",
            "JapanPics",
            "WinterPorn",
            "SpacePorn",
            "WaterPorn",
            "ImaginaryLandscapes",
            "SpaceFlightPorn",
            "CarPorn",
            "GunPorn",
            "RoomPorn",
            "CityPorn",
            "VillagePorn",
            "ArchitecturePorn",
            "InfrastructurePorn"

        )

        fun getRandomSubreddit(): String {
            val randomPosition = (0..subreddits.count() - 1).shuffled().first()
            return subreddits[randomPosition]
        }
    }
}
