package beepbeep.pixelsforreddit.reddit_api

import android.content.Context
import android.util.Log
import beepbeep.pixelsforreddit.common.defaultPrefs
import beepbeep.pixelsforreddit.common.get
import beepbeep.pixelsforreddit.common.save

class RedditPreference {
    companion object {
        const val SelectedSubreddit = "SelectedSubreddit"
        const val defaultSelectedSubreddit = "art"
        fun saveSelectedSubreddit(context: Context, subreddit: String) {
            context.defaultPrefs().save(SelectedSubreddit, subreddit)
        }

        fun getSelectedSubreddit(context: Context): String {
            val getSub = context.defaultPrefs().get(SelectedSubreddit, defaultSelectedSubreddit)
            Log.d("ddw", "getSub: $getSub")
            return getSub
        }
    }


}
