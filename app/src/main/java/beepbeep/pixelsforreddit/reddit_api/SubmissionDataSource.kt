package beepbeep.pixelsforreddit.reddit_api

import android.arch.paging.PageKeyedDataSource
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import net.dean.jraw.RedditClient
import net.dean.jraw.models.Submission

class SubmissionDataSource(val redditClient: RedditClient) : PageKeyedDataSource<String, Submission>() {
    val submissionPaginator = RedditAccountHelper.accountHelper.reddit.frontPage().build()
    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<String, Submission>) {
        Observable.just(submissionPaginator)
                .map { it.next() }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    callback.onResult(it, "", "")
                }
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, Submission>) {
        Observable.just(submissionPaginator)
                .map { it.next() }
                .subscribeOn(Schedulers.io())
                .subscribe {
                    callback.onResult(it, "")
                }
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, Submission>) {
        // we start from beginning of the list, do nothing
    }
}