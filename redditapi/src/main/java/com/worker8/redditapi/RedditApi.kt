package com.worker8.redditapi

import io.reactivex.Observable
import net.dean.jraw.RedditClient
import net.dean.jraw.http.oauth.OAuthData

class RedditApi {

    fun authRedditClient(): Observable<RedditClient> {
        return Observable.fromCallable {
            makeGuestRedditClient()
        }
    }

    fun makeGuestRedditClient(): RedditClient {
        val redditClient = RedditClient(userAgent)
        var authData: OAuthData = redditClient.oAuthHelper.easyAuth(credentials)
        redditClient.authenticate(authData)
        return redditClient
    }
}