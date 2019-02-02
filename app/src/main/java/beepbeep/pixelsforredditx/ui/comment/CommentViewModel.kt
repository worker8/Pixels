package beepbeep.pixelsforredditx.ui.comment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.nonNullValue
import com.github.kittinunf.result.failure
import com.worker8.redditapi.model.t1_comment.RedditReplyListingData
import com.worker8.redditapi.model.t3_link.RedditLinkListingData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject

class CommentViewModel() : ViewModel(), LifecycleObserver {
    lateinit var commentId: String
    lateinit var input: CommentContract.Input
    lateinit var repo: CommentRepo
    lateinit var viewAction: CommentContract.ViewAction
    private val disposableBag = CompositeDisposable()
    private val screenStateSubject = BehaviorSubject.createDefault<CommentContract.ScreenState>(CommentContract.ScreenState())
    private val currentScreenState get() = screenStateSubject.nonNullValue
    val screenState: Observable<CommentContract.ScreenState> by lazy { screenStateSubject.hide().observeOn(repo.getMainThread()) }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        Observable.fromCallable { viewAction.showLoadingProgressBar(true) }
            .subscribeOn(repo.getMainThread())
            .observeOn(repo.getBackgroundThread())
            .flatMap { repo.getComments(commentId) }
            .observeOn(repo.getMainThread())
            .doOnNext {
                it.failure {
                    // handles error
                }
            }
            .subscribe({ (resultPair: Pair<RedditLinkListingData, RedditReplyListingData>?, fuelError) ->
                resultPair?.let {
                    dispatch(currentScreenState.copy(it))
                }
                viewAction.showLoadingProgressBar(false)
                fuelError?.printStackTrace()
            }, {
                viewAction.showLoadingProgressBar(false)
                it.printStackTrace()
            })
            .addTo(disposableBag)
    }

    private fun dispatch(newScreenState: CommentContract.ScreenState) {
        screenStateSubject.onNext(newScreenState)
    }

    override fun onCleared() {
        super.onCleared()
        disposableBag.clear()
    }
}
