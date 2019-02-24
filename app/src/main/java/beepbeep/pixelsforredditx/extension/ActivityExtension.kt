package beepbeep.pixelsforredditx.extension

import android.content.Context
import android.net.ConnectivityManager

/**
 * Taken from https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
 */
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
