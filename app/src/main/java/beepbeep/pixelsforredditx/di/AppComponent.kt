package beepbeep.pixelsforredditx.di

import beepbeep.pixelsforredditx.PixelApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton
import kotlin.text.Typography.dagger

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityModule::class
])
interface AppComponent : AndroidInjector<PixelApplication> {

    @Component.Builder
    abstract class Builder {
        @BindsInstance
        abstract fun application(application: PixelApplication): Builder

        abstract fun build(): AppComponent
    }
}
