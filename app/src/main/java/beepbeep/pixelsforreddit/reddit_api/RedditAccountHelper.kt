package beepbeep.pixelsforreddit.reddit_api

import beepbeep.pixelsforreddit.PixelApplication.Companion.applicationContext
import net.dean.jraw.android.AndroidHelper
import net.dean.jraw.android.ManifestAppInfoProvider
import net.dean.jraw.android.SharedPreferencesTokenStore
import java.util.*

class RedditAccountHelper {
    companion object {
        val accountHelper by lazy {
            val provider = ManifestAppInfoProvider(context = applicationContext)
            val deviceUuid = UUID.randomUUID()
            val tokenStore = SharedPreferencesTokenStore(applicationContext).apply {
                load()
                autoPersist = true
            }
            AndroidHelper.accountHelper(provider, deviceUuid, tokenStore)
        }
    }
}