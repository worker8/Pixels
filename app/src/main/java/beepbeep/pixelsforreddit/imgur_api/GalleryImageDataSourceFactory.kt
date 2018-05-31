package beepbeep.pixelsforreddit.imgur_api

import android.arch.paging.DataSource
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage
import beepbeep.pixelsforreddit.imgur_api.network.GalleryService

class GalleryImageDataSourceFactory(val galleryService: GalleryService) : DataSource.Factory<Int, GalleryImage>() {
    val dataSource = GalleryImageDataSource(galleryService)
    override fun create(): DataSource<Int, GalleryImage> = dataSource
}