package com.example.redditapi

import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.worker8.redditapi.RedditApi
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RedditApi2Test {
    @Test
    fun testJraw() {
        val redditApi2 = RedditApi()
        Log.d("ddw","HELLOW!!")
        System.out.println("asdasdsadsadasdsaadsasadsadAAAA")

        redditApi2.getPosts()
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