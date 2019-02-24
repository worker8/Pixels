package beepbeep.pixelsforredditx

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho

class PixelApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Stetho.initializeWithDefaults(this)
    }

    companion object {
        private var appContext: Context? = null
    }
}
