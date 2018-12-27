package beepbeep.pixelsforreddit.extension

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

private val RecyclerView.onBottomDetectedSubject: PublishSubject<Unit> by lazy { PublishSubject.create<Unit>() }

private fun RecyclerView.makeBottomDetectionScrollListener() = object : RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        (layoutManager as LinearLayoutManager).apply {
            if (findLastVisibleItemPosition() + childCount >= itemCount) {
                onBottomDetectedSubject.onNext(Unit)
            }
        }
    }
}

val RecyclerView.onBottomDetectedObservable: Observable<Unit>
    get() = onBottomDetectedSubject.hide()

fun RecyclerView.initBottomDetectListener() {
    addOnScrollListener(makeBottomDetectionScrollListener())
}
