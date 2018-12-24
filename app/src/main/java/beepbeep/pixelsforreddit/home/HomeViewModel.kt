package beepbeep.pixelsforreddit.home

import android.arch.lifecycle.*
import android.util.Log
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.extension.nonNullValue
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class HomeViewModel(val input: HomeContract.Input, val repo: HomeRepo, val viewAction: HomeContract.ViewAction) : ViewModel(), LifecycleObserver {
    private val screenStateSubject = BehaviorSubject.createDefault<HomeContract.ScreenState>(HomeContract.ScreenState())
    var screenState = screenStateSubject.hide().observeOn(repo.getMainThread())

    private val currentScreenState
        get() = screenStateSubject.nonNullValue

    private fun dispatch(newScreenState: HomeContract.ScreenState) {
        screenStateSubject.onNext(newScreenState)
    }

    var isLoading = false

    private val disposableBag = CompositeDisposable()

    private fun setupNoNetwork() {
        if (!input.isConnectedToInternet()) {
            viewAction.showNoNetworkError()
        }
    }

    private val initialLoadTrigger: PublishSubject<Unit> = PublishSubject.create()

    private fun setupGetNewPosts() {
        input.apply {
            Observable.merge(initialLoadTrigger, loadMore)
                .observeOn(repo.getMainThread())
                .filter { isConnectedToInternet() }
                .filter { !isLoading }
                .doOnNext { viewAction.showLoadingProgressBar(true) }
                .doOnNext { isLoading = true }

                .observeOn(repo.getBackgroundThread())
                .flatMap { repo.getMorePosts() }

                .observeOn(repo.getMainThread())
                .doOnNext { (listing, fuelError) ->
                    Log.d("ddw", "$fuelError")
                    fuelError?.let {
                        it.printStackTrace()
                        viewAction.showLoadingProgressBar(false)

                    }
                    isLoading = false
                }
                .observeOn(repo.getMainThread())
                .subscribe({ (listing, fuelError) ->
                    viewAction.showLoadingProgressBar(false)
                    listing?.let {
                        dispatch(currentScreenState.copy(redditLinks = currentScreenState.redditLinks + it.value.getRedditImageLinks()))
                    }
                }, {
                    isLoading = false
                    viewAction.showLoadingProgressBar(false)
                })
                .addTo(disposableBag)
//
//            loadMore
//                .doOnNext { Log.d("ddw", "scrollevent - bottom...") }
//                .filter { !isLoading }
//                .doOnNext { isLoading = true }
//                .doOnNext { Log.d("ddw", "scrollevent - loading...") }
//                .delay(3000, TimeUnit.MILLISECONDS)
//                .observeOn(repo.getMainThread())
//                .doOnNext { Log.d("ddw", "scrollevent - done!") }
//                .subscribe { isLoading = false }
//                .addTo(disposableBag)

            initialLoadTrigger.onNext(Unit)
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
