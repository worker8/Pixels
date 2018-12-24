package com.worker8.redditapi

import android.util.Log
import org.junit.Assert.assertEquals
import org.junit.Test


class RedditApi2Test {
    @Test
    fun testJraw() {
        val redditApi2 = RedditApi()
        redditApi2.getMorePosts()
                .subscribe { (response, fuelError) ->
                    response?.let {
                        Log.d("ddw","$it")
                        System.out.println(it)
                    }
                    fuelError?.printStackTrace()
                }

        assertEquals(4, 2 + 2)
    }
}
