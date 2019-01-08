package beepbeep.pixelsforredditx.extension

import android.view.View

fun Boolean.visibility(): Int {
    if (this) {
        return View.VISIBLE
    }
    return View.GONE
}
