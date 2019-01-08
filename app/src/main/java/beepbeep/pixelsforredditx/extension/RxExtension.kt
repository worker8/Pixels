package beepbeep.pixelsforredditx.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject

fun Disposable.addTo(composite: CompositeDisposable) = composite.add(this)

// convenience value to avoid using !! everywhere
val <T> BehaviorSubject<T>.nonNullValue: T
    get() = value!!
