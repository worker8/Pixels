package beepbeep.pixelsforreddit.home.navDrawer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import beepbeep.pixelsforreddit.R
import com.worker8.redditapi.RedditApi
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main._navigation_drawer.view.*
import kotlinx.android.synthetic.main._navigation_item.view.*
import kotlinx.android.synthetic.main._navigation_random.view.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.navigational_parent.view.*

class NavigationDrawerView(val rootView: ViewGroup) {
    private val subredditSubject: PublishSubject<String> = PublishSubject.create()
    private val randomSubredditSelectedSubject: PublishSubject<String> = PublishSubject.create()
    val subredditChosen = subredditSubject.hide()
    val randomSubredditSelected = randomSubredditSelectedSubject.hide()

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
            randomizeButton.setOnClickListener {
                close()
                randomSubredditSelectedSubject.onNext(RedditApi.getRandomSubreddit())
            }
        }
    }

    fun clearHighlight() {
        rootView.apply {
            // TODO: refactor this fragile code, start with 3 because first one is header
            for (position in 3..homeNavigationDrawerContent.childCount - 1) {
                val itemContainer = homeNavigationDrawerContent.getChildAt(position)
                itemContainer.menuItemHighlight.visibility = View.GONE
            }
        }
    }

    fun setHightlight(subreddit: String) {
        clearHighlight()
        rootView.apply {
            // TODO: refactor this fragile code, start with 3 because first one is header
            for (position in 3..homeNavigationDrawerContent.childCount - 1) {
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
