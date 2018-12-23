package beepbeep.pixelsforreddit.home

import android.arch.lifecycle.*
import android.util.Log
import beepbeep.pixelsforreddit.extension.addTo
import com.worker8.redditapi.Listing
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class HomeViewModel(val input: HomeContract.Input, val repo: HomeRepo, val viewAction: HomeContract.ViewAction) : ViewModel(), LifecycleObserver {
    var pagedList: BehaviorSubject<Listing> = BehaviorSubject.create() //val networkState = homeRepo.getNetworkState()
    private val disposableBag = CompositeDisposable()

    private fun setupNoNetwork() {
        if (!input.isConnectedToInternet()) {
            viewAction.showNoNetworkError()
        }
    }

    private fun setupGetNewPosts() {
        input.apply {
            Observable.fromCallable { isConnectedToInternet() }
                .filter { it }
                .doOnNext { viewAction.showLoadingProgressBar(true) }
                .flatMap { repo.getPosts() }
                .doOnNext { (listing, fuelError) ->
                    Log.d("ddw", "$fuelError")
                    fuelError?.let {
                        it.printStackTrace()
                        viewAction.showLoadingProgressBar(false)
                    }
                }
                .subscribeOn(repo.getBackgroundThread())
                .observeOn(repo.getMainThread())
                .subscribe { (listing, fuelError) ->
                    viewAction.showLoadingProgressBar(false)
                    listing?.let {
                        pagedList.onNext(it)
                    }
                }
                .addTo(disposableBag)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        setupNoNetwork()
        setupGetNewPosts()


    }

    override fun onCleared() {
        super.onCleared()
        disposableBag.dispose()
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(val input: HomeContract.Input, val repo: HomeRepo, val viewAction: HomeContract.ViewAction) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(input, repo, viewAction) as T
    }
}
