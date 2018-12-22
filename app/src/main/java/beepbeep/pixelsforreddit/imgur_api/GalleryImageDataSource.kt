package beepbeep.pixelsforreddit.imgur_api

import android.arch.paging.PageKeyedDataSource
import beepbeep.pixelsforreddit.common.NetworkState
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage
import beepbeep.pixelsforreddit.imgur_api.network.GalleryService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class GalleryImageDataSource(val galleryService: GalleryService) : PageKeyedDataSource<Int, GalleryImage>() {
    val networkState = PublishSubject.create<NetworkState>()
    val disposableBag = CompositeDisposable()

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, GalleryImage>) {
        val firstPage = 0
        networkState.onNext(NetworkState.LOADING)
        galleryService.getGallery(firstPage)
            .map {
                it.data.removeAll(it.data.takeLast(it.data.size - 10))
                it
            }
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe {
                networkState.onNext(NetworkState.SUCCESS)
                callback.onResult(it.data, firstPage - 1, firstPage + 1)
            }
            .addTo(disposableBag)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryImage>) {
        networkState.onNext(NetworkState.LOADING)
        galleryService.getGallery(params.key)
            .map {
                it.data.removeAll(it.data.takeLast(it.data.size - 10))
                it
            }
            .delay(2, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .subscribe {
                networkState.onNext(NetworkState.SUCCESS)
                callback.onResult(it.data, params.key + 1)
            }
            .addTo(disposableBag)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, GalleryImage>) {
//        // we start from beginning of the list, do nothing
//        galleryService.getGallery(params.key)
//                .subscribeOn(Schedulers.io())
//                .subscribe { callback.onResult(it.data, params.key - 1) }
    }

    fun dispose() {
        disposableBag.dispose()
    }
}
