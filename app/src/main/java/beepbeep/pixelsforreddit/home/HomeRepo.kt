package beepbeep.pixelsforreddit.home

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import beepbeep.pixelsforreddit.BuildConfig
import beepbeep.pixelsforreddit.imgur_api.GalleryImageDataSourceFactory
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage
import beepbeep.pixelsforreddit.imgur_api.network.GalleryService
import beepbeep.pixelsforreddit.imgur_api.network.retrofit
import io.reactivex.Observable
import net.dean.jraw.RedditClient
import net.dean.jraw.http.OkHttpNetworkAdapter
import net.dean.jraw.http.UserAgent
import net.dean.jraw.oauth.Credentials
import net.dean.jraw.oauth.OAuthHelper
import java.util.*

class HomeRepo() {
    val dataSourceFactory = GalleryImageDataSourceFactory(retrofit.create(GalleryService::class.java))

    fun getUserlessRedditClient(): Observable<RedditClient> {
        return Observable.fromCallable {
            val credentials = Credentials.userlessApp(BuildConfig.REDDIT_API_CLIENT_ID, UUID.randomUUID())
            OAuthHelper.automatic(OkHttpNetworkAdapter(UserAgent("Android App")), credentials)
        }
    }

    fun getDataSource(): Observable<PagedList<GalleryImage>> {
        return RxPagedListBuilder(dataSourceFactory, 10).buildObservable()
    }

    fun getNetworkState() = dataSourceFactory.dataSource.networkState.hide()
}