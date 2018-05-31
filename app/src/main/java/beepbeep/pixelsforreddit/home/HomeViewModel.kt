package beepbeep.pixelsforreddit.home

import android.arch.lifecycle.*
import android.arch.paging.PagedList
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.imgur_api.model.GalleryImage
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class HomeViewModel(val homeRepo: HomeRepo) : ViewModel(), LifecycleObserver {
    var pagedList: PublishSubject<PagedList<GalleryImage>> = PublishSubject.create()
    val networkState = homeRepo.getNetworkState()

    private val disposableBag = CompositeDisposable()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        homeRepo.getDataSource()
                .subscribe { pagedList.onNext(it) }
                .addTo(disposableBag)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        disposableBag.dispose()
    }

}

class HomeViewModelFactory(val homeRepo: HomeRepo) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepo) as T
    }
}
