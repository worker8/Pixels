package beepbeep.pixelsforreddit

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class PixelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        _applicationContext = this
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        private var _applicationContext: Context? = null
    }
}
