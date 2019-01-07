package beepbeep.pixelsforreddit.common

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import beepbeep.pixelsforreddit.extension.ofType

fun Context.defaultPrefs(): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
fun Context.customPrefs(name: String): SharedPreferences = getSharedPreferences(name, Context.MODE_PRIVATE)

fun SharedPreferences.save(key: String, value: Any) {
    edit().apply {
        when (value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Float -> putFloat(key, value)
            is Long -> putLong(key, value)
            else -> throw UnsupportedOperationException("Unsupported Type")
        }
    }.apply()
}

inline fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T): T {
    defaultValue.ofType<String> {
        return getString(key, it) as T
    }

    defaultValue.ofType<Int> {
        return getInt(key, it) as T
    }

    defaultValue.ofType<Boolean> {
        return getBoolean(key, it) as T
    }

    defaultValue.ofType<Float> {
        return getFloat(key, it) as T
    }

    defaultValue.ofType<Long> {
        return getLong(key, it) as T
    }

    throw UnsupportedOperationException("Unsupported Type")
}
