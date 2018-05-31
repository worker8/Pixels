package beepbeep.pixelsforreddit.imgur_api.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient

val imgurOkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(ImgurApiHeaderInterceptor())
        .addNetworkInterceptor(StethoInterceptor())
        .build()