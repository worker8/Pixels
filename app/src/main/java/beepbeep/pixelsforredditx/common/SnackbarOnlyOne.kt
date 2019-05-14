package beepbeep.pixelsforredditx.common

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.annotation.StringRes
import beepbeep.pixelsforredditx.R
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
                val backgroundColor = getThemeBackgroundColor(this@apply.view.context)
                this@apply.view.setBackgroundColor(backgroundColor)
            }
            .show()
    }

    fun dismiss() {
        snackbar?.dismiss()
    }

    fun getThemeBackgroundColor(context: Context): Int {
        val value = TypedValue()
        context.getTheme().resolveAttribute(R.attr.background, value, true)
        return value.data
    }
}
