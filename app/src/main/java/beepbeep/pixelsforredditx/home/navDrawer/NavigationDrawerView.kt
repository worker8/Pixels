package beepbeep.pixelsforredditx.home.navDrawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.pixelsforredditx.R
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.checkedChanges
import com.worker8.redditapi.RedditApi
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main._navigation_about.view.*
import kotlinx.android.synthetic.main._navigation_drawer.view.*
import kotlinx.android.synthetic.main._navigation_item.view.*
import kotlinx.android.synthetic.main._navigation_night_mode.view.*
import kotlinx.android.synthetic.main._navigation_random.view.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.navigational_parent.view.*

class NavigationDrawerView(private val rootView: ViewGroup) {
    private val subredditSubject: PublishSubject<String> = PublishSubject.create()
    val subredditChosen = subredditSubject.hide()
    val randomButtonClick by lazy {
        rootView.randomizeButton.clicks()
            .doOnNext { close() }
            .map { RedditApi.getRandomSubreddit() }
    }
    val aboutButtonClick by lazy {
        rootView.aboutButton.clicks()
            .doOnNext { close() }
    }
    val nightModeCheckChanged by lazy {
        rootView.nightModeSwitch.checkedChanges()
            .doOnNext { close() }
    }

    init {
        rootView.apply {
            RedditApi.subreddits.forEach { subreddit ->
                val menuItemView = LayoutInflater.from(context).inflate(R.layout._navigation_item, this, false)
                menuItemView.menuItemTextView.text = subreddit
                menuItemView.setOnClickListener {
                    close()
                    subredditSubject.onNext(subreddit)
                }
                homeNavigationDrawerContent.addView(menuItemView)
            }
            homeBurger.setOnClickListener {
                if (homeDrawerLayout.isDrawerOpen(homeNavigationDrawer)) {
                    close()
                } else {
                    open()
                }
            }
        }
    }

    private fun clearHighlight() {
        rootView.apply {
            // TODO: refactor this fragile code, start with 4 because first one is header
            for (position in 4 until homeNavigationDrawerContent.childCount) {
                val itemContainer = homeNavigationDrawerContent.getChildAt(position)
                itemContainer.menuItemHighlight.visibility = View.GONE
            }
        }
    }

    fun setHighlight(subreddit: String) {
        clearHighlight()
        rootView.apply {
            // TODO: refactor this fragile code, start with 4 because first one is header
            for (position in 4 until homeNavigationDrawerContent.childCount) {
                val itemContainer = homeNavigationDrawerContent.getChildAt(position)
                if (itemContainer.menuItemTextView.text == subreddit) {
                    itemContainer.menuItemHighlight.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun open() {
        rootView.apply {
            homeDrawerLayout.openDrawer(homeNavigationDrawer)
        }
    }

    private fun close() {
        rootView.apply {
            homeDrawerLayout.closeDrawer(homeNavigationDrawer)
        }
    }

}
