package beepbeep.pixelsforreddit.extension

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity

inline fun <reified T : ViewModel> FragmentActivity.getViewModel() =
        ViewModelProviders.of(this).get(T::class.java)
