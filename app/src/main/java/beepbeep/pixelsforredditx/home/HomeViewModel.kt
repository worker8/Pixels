package beepbeep.pixelsforredditx.home

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.nonNullValue
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class HomeViewModel : ViewModel(), LifecycleObserver {
    lateinit var input: HomeContract.Input
    lateinit var repo: HomeRepo
    lateinit var viewAction: HomeContract.ViewAction

    private val screenStateSubject = BehaviorSubject.createDefault<HomeContract.ScreenState>(HomeContract.ScreenState())
    val screenState: Observable<HomeContract.ScreenState> by lazy { screenStateSubject.hide().observeOn(repo.getMainThread()) }

    private val currentScreenState get() = screenStateSubject.nonNullValue
    private var isLoading = false
    private val disposableBag = CompositeDisposable()
    private val initialLoadTrigger: PublishSubject<Unit> = PublishSubject.create()

    private val loadMoreShared: Observable<Unit>
        get() = input.loadMore.share()
    private val retryShared: Observable<Unit>
        get() = input.retry.share()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        setupNoNetwork()
        setupGetNewPosts()
        setupNavbar()
    }

    private fun setupNavbar() {
        input.apply {
            aboutClicked
                .observeOn(repo.getMainThread())
                .subscribe { viewAction.navigateToAboutPage() }
                .addTo(disposableBag)

            nightModeCheckChanged
                .skip(1)
                .observeOn(repo.getMainThread())
                .subscribe { viewAction.reRenderOnThemeChange(it) }
                .addTo(disposableBag)
        }
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
                    dispatch(currentScreenState.copy(redditLinks = listOf()))
                    repo.selectSubreddit(it)
                    viewAction.updateToolbarSubredditText(it)
                }
                .share()

            postClicked
                .observeOn(repo.getMainThread())
                .subscribe { viewAction.navigateToCommentActivity(it) }
                .addTo(disposableBag)

            Observable.merge(initialLoadTrigger, loadMore, retry, subSelectedShared)
                .doOnNext { viewAction.navSetHighlight(repo.getSubredditSharedPreference()) }
                .filter { isConnectedToInternet() && !isLoading }
                .observeOn(repo.getMainThread())
                .doOnNext { setLoadingUi(true) }
                .observeOn(repo.getBackgroundThread())
                .flatMap { repo.getMorePosts() }
                .observeOn(repo.getMainThread())
                .subscribe({ (listing, _) ->
                    setLoadingUi(false)
                    listing?.let {
                        dispatch(currentScreenState.copy(redditLinks = currentScreenState.redditLinks + it.value.getRedditImageLinks()))
                    }
                }, {
                    setLoadingUi(false)
                    viewAction.showGenericErrorMessage() // unrecoverable error
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
        disposableBag.clear()
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel() as T
    }
}
