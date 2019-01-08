package beepbeep.pixelsforredditx.preference

import android.content.Context
import beepbeep.pixelsforredditx.common.defaultPrefs
import beepbeep.pixelsforredditx.common.get
import beepbeep.pixelsforredditx.common.save

class ThemePreference {
    companion object {
        private const val PreferenceTheme = "PreferenceTheme"
        private const val defaultTheme = false // false = light, true = dark

        fun saveThemePreference(context: Context, isDark: Boolean) {
            context.defaultPrefs().save(PreferenceTheme, isDark)
        }

        fun getThemePreference(context: Context): Boolean {
            return context.defaultPrefs().get(PreferenceTheme, defaultTheme)
        }
    }
}
