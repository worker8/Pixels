package com.worker8.redditapi

import net.dean.jraw.RedditClient
import net.dean.jraw.http.UserAgent
import net.dean.jraw.http.oauth.Credentials
import net.dean.jraw.http.oauth.OAuthData
import net.dean.jraw.paginators.SubredditPaginator
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*


class RedditClientTest {


    @Test
    fun testJraw() {
        val redditClient = RedditApi().makeGuestRedditClient()
        val paginator = SubredditPaginator(redditClient).apply { setSubreddit("pics") }
        val firstPage = paginator.next()
        firstPage.forEach {
            System.out.println(it.title)
        }

        assertEquals(4, 2 + 2)
    }
}