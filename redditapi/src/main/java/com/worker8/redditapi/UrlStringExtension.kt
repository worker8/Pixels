package com.worker8.redditapi

/**
 * https://stackoverflow.com/a/169631/75579
 */
fun String.isImageUrl(): Boolean {
    return "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|gif|png))(?:\\?([^#]*))?(?:#(.*))?".toRegex().matches(this)
}
