package beepbeep.pixelsforreddit.preference

import android.content.Context
import beepbeep.pixelsforreddit.common.defaultPrefs
import beepbeep.pixelsforreddit.common.get
import beepbeep.pixelsforreddit.common.save

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
