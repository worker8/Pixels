package com.worker8.redditapi.common

inline fun <T, T2, R> T?.letWith(secondArg: T2?, block: (T, T2) -> R): R? {
    return this?.let {
        secondArg?.let {
            block(this, secondArg)
        }
    }
}

inline fun <reified T> Any.ofType(block: (T) -> Unit) {
    if (this is T) {
        block(this as T)
    }
}
