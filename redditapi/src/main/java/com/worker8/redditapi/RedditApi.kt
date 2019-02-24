package com.worker8.redditapi

import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rxObject
import com.github.kittinunf.result.Result
import com.google.gson.GsonBuilder
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData
import com.worker8.redditapi.model.t1_comment.deserializer.RedditCommentDeserializer
import com.worker8.redditapi.model.t1_comment.deserializer.RedditCommentListingObjectDeserializer
import com.worker8.redditapi.model.t1_comment.deserializer.RedditReplyListingDataDeserializer
import com.worker8.redditapi.model.t1_comment.deserializer.T1RedditObjectDeserializer
import com.worker8.redditapi.model.t1_comment.response.RedditCommentListingObject
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import com.worker8.redditapi.model.t3_link.response.RedditLinkListingObject
import io.reactivex.Observable

class RedditApi(val subreddit: String = defaultSelectedSubreddit) {

    private var after = ""

    init {
        FuelManager.instance.apply {
//            hook = StethoHook() -- enable once fuel new version is released
        }
    }

    fun getMorePosts(): Observable<Result<RedditLinkListingObject, FuelError>> =
        "${REDDIT_API_BASE}r/$subreddit.json?after=$after"
            .httpGet()
            .rxObject(RedditLinkListingObject.Deserializer())
            .toObservable()
            .doOnNext { (listing, _) ->
                listing?.value?.after?.also { after = it }
            }

    fun getComment(commentId: String): Observable<Result<Pair<RedditLinkListingData, RedditReplyListingData>, FuelError>> =
        "${REDDIT_API_BASE}comments/$commentId.json"
            .httpGet()
            .rxObject(RedditCommentDeserializer())
            .toObservable()

    companion object {

        private const val REDDIT_API_BASE = "https://www.reddit.com/"

        val gson = GsonBuilder()
            .registerTypeAdapter(RedditCommentListingObject::class.java, RedditCommentListingObjectDeserializer())
            .registerTypeAdapter(RedditReplyDynamicObject.T1RedditObject::class.java, T1RedditObjectDeserializer())
            .registerTypeAdapter(RedditReplyListingData::class.java, RedditReplyListingDataDeserializer())
            .create()

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
            val randomPosition = (0 until subreddits.count()).shuffled().first()
            return subreddits[randomPosition]
        }
    }
}
