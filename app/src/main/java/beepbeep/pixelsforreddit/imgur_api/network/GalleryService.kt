package beepbeep.pixelsforreddit.imgur_api.network

import beepbeep.pixelsforreddit.imgur_api.model.ImgurResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GalleryService {
    @GET("gallery/r/pics/time/{page}")
    fun getGallery(@Path("page") page: Int): Observable<ImgurResponse>
}