package beepbeep.pixelsforredditx.extension

import android.text.format.DateUtils
import java.util.*

fun Int.toRelativeTimeString(): String {
    val oldUnixTime = this.toLong() * 1000
    val nowUnixTime = Date().time
    return DateUtils.getRelativeTimeSpanString(oldUnixTime, nowUnixTime, DateUtils.MINUTE_IN_MILLIS).toString()
}
