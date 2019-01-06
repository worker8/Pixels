package beepbeep.pixelsforreddit.home

import android.arch.lifecycle.*
import beepbeep.pixelsforreddit.extension.addTo
import beepbeep.pixelsforreddit.extension.nonNullValue
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class HomeViewModel(val input: HomeContract.Input, val repo: HomeRepo, val viewAction: HomeContract.ViewAction) : ViewModel(), LifecycleObserver {
    private val screenStateSubject = BehaviorSubject.createDefault<HomeContract.ScreenState>(HomeContract.ScreenState())
    var screenState: Observable<HomeContract.ScreenState> = screenStateSubject.hide().observeOn(repo.getMainThread())

    private val currentScreenState get() = screenStateSubject.nonNullValue
    private var isLoading = false
    private val disposableBag = CompositeDisposable()
    private val initialLoadTrigger: PublishSubject<Unit> = PublishSubject.create()
    private val loadMoreShared by lazy { input.loadMore.share() }
    private val retryShared by lazy { input.retry.share() }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        setupNoNetwork()
        setupGetNewPosts()
    }

    private fun setupNoNetwork() {
        Observable.merge(initialLoadTrigger, loadMoreShared, retryShared)
            .filter { !input.isConnectedToInternet() }
            .observeOn(repo.getMainThread())
            .subscribe { viewAction.showNoNetworkErrorSnackbar() }
            .addTo(disposableBag)
    }

    private fun setupGetNewPosts() {
        input.apply {
            val subSelectedShared = Observable.merge(subredditSelected, randomSubredditSelected)
                .doOnNext {
                    repo.saveSubredditSharedPreference(it)
                    dispatch(currentScreenState.copy(listOf()))
                    repo.selectSubreddit(it)
                    viewAction.updateToolbarSubredditText(it)
                }
                .share()
            Observable.merge(initialLoadTrigger, loadMore, retry, subSelectedShared)
                .doOnNext { viewAction.navSetHightlight(repo.getSubredditSharedPreference()) }
                .filter { isConnectedToInternet() && !isLoading }
                .observeOn(repo.getMainThread())
                .doOnNext { setLoadingUi(true) }
                .observeOn(repo.getBackgroundThread())
                .flatMap { repo.getMorePosts() }
                .observeOn(repo.getMainThread())
                .subscribe({ (listing, fuelError) ->
                    setLoadingUi(false)
                    listing?.let {
                        dispatch(currentScreenState.copy(redditLinks = currentScreenState.redditLinks + it.value.getRedditImageLinks()))
                    }
                }, {
                    setLoadingUi(false)
                    it.printStackTrace()
                })
                .addTo(disposableBag)

            initialLoadTrigger.onNext(Unit)
        }
    }

    private fun setLoadingUi(showOrHide: Boolean) {
        if (showOrHide) {
            isLoading = true
            if (currentScreenState.redditLinks.isEmpty()) {
                viewAction.showLoadingProgressBar(true)
            } else {
                viewAction.showBottomLoadingProgresBar(true)
            }
            viewAction.dismissNoNetworkErrorSnackbar()
        } else {
            isLoading = false
            viewAction.showLoadingProgressBar(false)
            viewAction.showBottomLoadingProgresBar(false)
        }
    }

    private fun dispatch(newScreenState: HomeContract.ScreenState) {
        screenStateSubject.onNext(newScreenState)
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
