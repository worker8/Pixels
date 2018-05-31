package beepbeep.pixelsforreddit.imgur_api.network

import beepbeep.pixelsforreddit.imgur_api.IMGUR_API_BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
        .baseUrl(IMGUR_API_BASE_URL)
        .client(imgurOkHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()