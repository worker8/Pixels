package beepbeep.pixelsforredditx.common

import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

/**
 * this is a class to ensure there's only one snackbar at a time, if it's already shown, then it won't show again
 */
class SnackbarOnlyOne {
    private var snackbar: Snackbar? = null

    fun show(view: View, @StringRes resId: Int, duration: Int, @StringRes actionResId: Int, actionCallback: () -> Unit) {
        if (snackbar != null) {
            snackbar?.show()
        }
        Snackbar.make(view, resId, duration)
            .apply {
                setAction(actionResId) { actionCallback.invoke() }
                addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                        super.onDismissed(transientBottomBar, event)
                        snackbar = null
                    }
                })
                snackbar = this
            }
            .show()
    }

    fun dismiss() {
        snackbar?.dismiss()
    }
}
