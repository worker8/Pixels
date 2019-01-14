package beepbeep.pixelsforredditx.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import androidx.fragment.app.FragmentActivity

inline fun <reified T : ViewModel> FragmentActivity.getViewModel() =
    ViewModelProviders.of(this).get(T::class.java)

/**
 * Taken from https://developer.android.com/training/monitoring-device-state/connectivity-monitoring
 */
fun Context.isConnectedToInternet(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    return connectivityManager.activeNetworkInfo?.isConnectedOrConnecting ?: false
}
