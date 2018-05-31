package beepbeep.pixelsforreddit.extension

import android.widget.ImageView

fun ImageView.resize(_width: Int?, _height: Int?) {
    _width.letWith(_height) { width1, height1 ->
        layoutParams.apply {
            width = width1
            height = height1
        }
    }
}