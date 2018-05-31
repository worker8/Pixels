package beepbeep.pixelsforreddit.reddit_api

import android.arch.paging.DataSource
import net.dean.jraw.RedditClient
import net.dean.jraw.models.Submission

class SubmissionDataSourceFactory(val redditClient: RedditClient) : DataSource.Factory<String, Submission>() {
    override fun create(): DataSource<String, Submission> {
        return SubmissionDataSource(redditClient)
    }

}