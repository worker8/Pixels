package beepbeep.pixelsforreddit.home

import android.arch.lifecycle.*
import beepbeep.pixelsforreddit.extension.addTo
import com.worker8.redditapi.Listing
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class HomeViewModel(val repo: HomeRepo2) : ViewModel(), LifecycleObserver {
    var pagedList: BehaviorSubject<Listing> = BehaviorSubject.create()
    //val networkState = homeRepo.getNetworkState()
    private val disposableBag = CompositeDisposable()


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
//        repo.getUserlessPaginator()
//                .map { paginator -> repo.getSubmissions() }
//                .subscribeOn(repo.getBackgroundThread())
//                .observeOn(repo.getMainThread())
//                .map { listing ->
//                    listing.filter { submission -> submission.url.isImageUrl() && !submission.isNsfw }
//                }
//                .subscribe { listing ->
//                    pagedList.onNext(listing)
//                    listing.forEach {
//                        Log.d("ddw", "[filtered] ${it.url}")
//                    }
//                }
//                .addTo(disposableBag)
        repo.getPosts()
                .doOnNext { (listing, fuelError) ->
                    fuelError?.printStackTrace()
                }
                .subscribeOn(repo.getBackgroundThread())
                .observeOn(repo.getMainThread())
                .subscribe { (listing, fuelError) ->
                    listing?.let {
                        pagedList.onNext(it)
                    }
                }
                .addTo(disposableBag)

    }

    override fun onCleared() {
        super.onCleared()
//        homeRepo.dispose()
        disposableBag.dispose()
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(val homeRepo: HomeRepo2) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(homeRepo) as T
    }
}
