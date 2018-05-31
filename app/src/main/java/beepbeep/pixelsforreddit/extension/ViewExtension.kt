package beepbeep.pixelsforreddit.extension

import android.view.View

fun View.dpWidth(): Int {
    val density = resources.displayMetrics.density
    return (width / density).toInt()
}

fun View.dpHeight(): Int {
    val density = resources.displayMetrics.density
    return (height / density).toInt()
}