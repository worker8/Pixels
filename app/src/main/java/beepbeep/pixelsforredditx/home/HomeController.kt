package beepbeep.pixelsforredditx.home

import android.util.Log
import com.airbnb.epoxy.TypedEpoxyController
import com.worker8.redditapi.model.t3_link.response.RedditLinkObject
import io.reactivex.subjects.PublishSubject

class HomeController() : TypedEpoxyController<List<RedditLinkObject>>() {
    private val postClickedSubject: PublishSubject<String> = PublishSubject.create()
    val postClickedObservable = postClickedSubject.hide()
//    @AutoModel
//    lateinit var homeViewHolder2: HomeViewHolder2_

    override fun buildModels(redditLinkObjectList: List<RedditLinkObject>) {
        redditLinkObjectList.forEach { redditLinkObject ->
            Log.d("ddw", "redditLinkObject.value.id.hashCode(): ${redditLinkObject.value.id.hashCode()}")
            HomeViewHolder2_()
                .id(redditLinkObject.value.id.hashCode())
                .redditLink(redditLinkObject)
                .callback { commentId -> postClickedSubject.onNext(commentId) }
                .addTo(this@HomeController)
        }
    }
}
