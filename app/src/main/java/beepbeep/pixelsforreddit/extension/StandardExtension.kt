package beepbeep.pixelsforreddit.extension

inline fun <T, T2, R> T?.letWith(secondArg: T2?, block: (T, T2) -> R): R? {
    return this?.let {
        secondArg?.let {
            block(this, secondArg)
        }
    }
}