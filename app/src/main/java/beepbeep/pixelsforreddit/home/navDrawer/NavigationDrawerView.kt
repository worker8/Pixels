package beepbeep.pixelsforreddit.home.navDrawer

import android.view.LayoutInflater
import android.view.ViewGroup
import beepbeep.pixelsforreddit.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main._navigation_drawer.view.*
import kotlinx.android.synthetic.main._navigation_item.view.*
import kotlinx.android.synthetic.main.activity_home.view.*
import kotlinx.android.synthetic.main.navigational_parent.view.*

class NavigationDrawerView(val rootView: ViewGroup) {
    private val subredditSubject: PublishSubject<String> = PublishSubject.create()
    val subredditChosen = subredditSubject.hide()

    init {
        rootView.apply {
            val subreddits = listOf("pics", "aww", "art", "IDAP", "doodles", "earthporn", "mildlyInteresting",
                "food",
                "FoodPorn",
                "DessertPorn",
                "EarthPorn",
                "JapanPics",
                "WinterPorn",
                "SpacePorn",
                "WaterPorn",
                "ImaginaryLandscapes",
                "SpaceFlightPorn"
            )
            subreddits.forEach { subreddit ->
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
