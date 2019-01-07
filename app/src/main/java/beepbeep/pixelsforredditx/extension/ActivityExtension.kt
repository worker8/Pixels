package beepbeep.pixelsforredditx.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.FragmentActivity

inline fun <reified T : ViewModel> FragmentActivity.getViewModel() =
    ViewModelProviders.of(this).get(T::class.java)

/**
 * Taken from https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
 */
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
