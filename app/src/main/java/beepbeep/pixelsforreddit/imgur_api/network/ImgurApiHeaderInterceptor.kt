package beepbeep.pixelsforreddit.imgur_api.network

import beepbeep.pixelsforreddit.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class ImgurApiHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Client-ID ${BuildConfig.IMGUR_API_CLIENT_ID}")
            .build()
        return chain.proceed(request)
    }
}
