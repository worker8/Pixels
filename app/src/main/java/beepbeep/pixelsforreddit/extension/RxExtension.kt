package beepbeep.pixelsforreddit.extension

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(composite: CompositeDisposable) = composite.add(this)