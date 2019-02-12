package beepbeep.pixelsforredditx.di

import beepbeep.pixelsforredditx.ui.home.HomeActivity
import beepbeep.pixelsforredditx.ui.comment.CommentActivity
import beepbeep.pixelsforredditx.ui.comment.di.CommentModule
import beepbeep.pixelsforredditx.ui.home.di.HomeModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeMainActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [CommentModule::class])
    abstract fun contributeSecondActivity(): CommentActivity
}
