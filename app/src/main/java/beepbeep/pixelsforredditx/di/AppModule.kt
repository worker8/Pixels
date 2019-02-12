package beepbeep.pixelsforredditx.di

import android.app.Application
import android.content.Context
import beepbeep.pixelsforredditx.PixelApplication
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class AppModule() {
    @Binds
    abstract fun provideContext(application: PixelApplication): Context
}
