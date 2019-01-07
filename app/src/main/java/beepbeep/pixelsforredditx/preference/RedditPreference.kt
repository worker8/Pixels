package beepbeep.pixelsforredditx.preference

import android.content.Context
import beepbeep.pixelsforredditx.common.defaultPrefs
import beepbeep.pixelsforredditx.common.get
import beepbeep.pixelsforredditx.common.save

class RedditPreference {
    companion object {
        private const val PreferenceSelectedSubreddit = "PreferenceSelectedSubreddit"
        private const val defaultSelectedSubreddit = "art"

        fun saveSelectedSubreddit(context: Context, subreddit: String) {
            context.defaultPrefs().save(PreferenceSelectedSubreddit, subreddit)
        }

        fun getSelectedSubreddit(context: Context): String {
            return context.defaultPrefs().get(PreferenceSelectedSubreddit, defaultSelectedSubreddit)
        }
    }
}
