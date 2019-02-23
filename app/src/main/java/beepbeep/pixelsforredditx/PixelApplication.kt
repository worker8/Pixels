package beepbeep.pixelsforredditx

import android.app.Application
import android.content.Context

class PixelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        _applicationContext = this
    }

    companion object {
        private var _applicationContext: Context? = null
    }
}
