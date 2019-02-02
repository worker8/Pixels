package beepbeep.pixelsforredditx.ui.comment

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.nonNullValue
import beepbeep.pixelsforredditx.extension.ofType
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.github.kittinunf.result.failure
import com.worker8.redditapi.model.listing.RedditCommentDataType
import com.worker8.redditapi.model.t1_comment.RedditReply
import com.worker8.redditapi.model.t1_comment.RedditReplyListingData
import com.worker8.redditapi.model.t3_link.RedditLinkListingData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class CommentViewModel() : ViewModel(), LifecycleObserver {
    lateinit var commentId: String
    lateinit var input: CommentContract.Input
    lateinit var repo: CommentRepo
    lateinit var viewAction: CommentContract.ViewAction
    private val disposableBag = CompositeDisposable()
    private val screenStateSubject = BehaviorSubject.createDefault<CommentContract.ScreenState>(CommentContract.ScreenState())
    private val currentScreenState get() = screenStateSubject.nonNullValue
    private val initialLoadTrigger: PublishSubject<Unit> = PublishSubject.create()
    val screenState: Observable<CommentContract.ScreenState> by lazy { screenStateSubject.hide().observeOn(repo.getMainThread()) }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        setupNoNetwork()
        setupGetComments()
        initialLoadTrigger.onNext(Unit)
    }

    private fun setupGetComments() {
        Observable.merge(initialLoadTrigger, input.retry)
            .filter { input.isConnectedToInternet() }
            .observeOn(repo.getMainThread())
            .doOnNext { viewAction.showLoadingProgressBar(true) }
            .observeOn(repo.getBackgroundThread())
            .flatMap { repo.getComments(commentId) }
            .observeOn(repo.getMainThread())
            .doOnNext {
                it.failure {
                    // TODO: handles error
                }
            }
            .observeOn(repo.getBackgroundThread())
            .map { (resultPair: Pair<RedditLinkListingData, RedditReplyListingData>?, fuelError) ->
                val dataRows = mutableListOf<CommentAdapter.CommentViewType>()
                resultPair?.let { linkAndComments ->
                    val (titleListing, commentListing) = linkAndComments
                    val flattenComments: List<CommentAdapter.CommentViewType> = RedditReply.flattenComments(commentListing).map { (level, redditCommentDataType) ->
                        redditCommentDataType.ofType<RedditCommentDataType.TMore> {
                            return@map CommentAdapter.CommentViewType.ItemViewMore(level to it)
                        }
                        val commentData = (redditCommentDataType as RedditCommentDataType.RedditCommentData)
                        val concatenatedInfoString = commentData.run {
                            author + " · " + score.toString() + " points · " + created.toRelativeTimeString()
                        }
                        return@map CommentAdapter.CommentViewType.Item(level = " ".repeat(level), concatenatedInfoString = concatenatedInfoString, commentHtmlString = commentData.body_html)
                    }

                    dataRows.add(CommentAdapter.CommentViewType.Header(titleListing))
                    if (flattenComments.isEmpty()) {
                        dataRows.add(CommentAdapter.CommentViewType.Empty())
                    } else {
                        dataRows.addAll(flattenComments)
                    }
                }
                dataRows
            }
            .filter { it.isNotEmpty() }
            .observeOn(repo.getMainThread())
            .subscribe({
                dispatch(currentScreenState.copy(it))
            }, {
                viewAction.showLoadingProgressBar(false)
                it.printStackTrace()
            })
            .addTo(disposableBag)
    }

    private fun setupNoNetwork() {
        Observable.merge(initialLoadTrigger, input.retry)
            .filter { !input.isConnectedToInternet() }
            .observeOn(repo.getMainThread())
            .subscribe { viewAction.showNoNetworkErrorSnackbar() }
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
