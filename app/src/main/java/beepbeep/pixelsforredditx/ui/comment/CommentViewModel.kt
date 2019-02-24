package beepbeep.pixelsforredditx.ui.comment

import android.text.Html
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel
import beepbeep.pixelsforredditx.extension.addTo
import beepbeep.pixelsforredditx.extension.nonNullValue
import beepbeep.pixelsforredditx.extension.ofType
import beepbeep.pixelsforredditx.extension.toRelativeTimeString
import com.github.kittinunf.result.failure
import com.worker8.redditapi.model.t1_comment.data.RedditCommentDynamicData
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject
import com.worker8.redditapi.model.t3_link.data.RedditLinkListingData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

class CommentViewModel : ViewModel(), LifecycleObserver {
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
            .map { (resultPair: Pair<RedditLinkListingData, RedditReplyListingData>?, _) ->
                val dataRows = mutableListOf<CommentAdapter.CommentViewType>()
                resultPair?.let { linkAndComments ->
                    val (titleListing, commentListing) = linkAndComments
                    val originalPoster = titleListing.valueList[0].value.author
                    val flattenComments: List<CommentAdapter.CommentViewType> = RedditReplyDynamicObject.flattenComments(commentListing).map { (level, redditCommentDataType) ->
                        redditCommentDataType.ofType<RedditCommentDynamicData.TMore> {
                            return@map CommentAdapter.CommentViewType.ItemViewMore(id = it.id, itemMoreData = level to it)
                        }
                        val commentData = (redditCommentDataType as RedditCommentDynamicData.T1RedditCommentData)
                        val highlightedAuthor = if (commentData.author == originalPoster) {
                            "<strong><u><font color='#FF6F00'>${commentData.author}</font></u></strong>"
                        } else {
                            "<font color='#1E88E5'>${commentData.author}</font>"
                        }
                        val concatenatedInfoString = commentData.run {
                            Html.fromHtml(highlightedAuthor + " · " + score.toString() + " points · " + created.toRelativeTimeString())
                        }
                        val htmlString = Html.fromHtml(Html.fromHtml(commentData.body_html).toString()).trim()
                        return@map CommentAdapter.CommentViewType.Item(id = commentData.id, level = " ".repeat(level), concatenatedInfoString = concatenatedInfoString, commentHtmlString = htmlString)
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
            .observeOn(repo.getMainThread())
            .doOnNext { viewAction.showLoadingProgressBar(false) }
            .filter { it.isNotEmpty() }
            .subscribe({
                dispatch(currentScreenState.copy(dataRows = it))
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
