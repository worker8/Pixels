package com.worker8.redditapi.model.t1_comment

import com.google.gson.annotations.SerializedName

data class RedditCommentListingData(
    val after: String = "",
    val before: String = "",
    @SerializedName("children")
    val valueList: List<RedditCommentObject> = listOf(),
    val dist: Int = 0,
    val modhash: String = ""
) {
    companion object {
        fun flattenComments(rootNode: RedditCommentListingData): MutableList<Pair<Int, String>> {
            val store: MutableList<Pair<Int, String>> = mutableListOf()
            traverse(rootNode, 0) { level, comment ->
                store.add(level to comment)
            }
            return store
        }

        fun traverse(rootNode: RedditCommentListingData, level: Int, callback: (Int, String) -> Unit = { _, _ -> }) {
            if (rootNode.valueList.isEmpty()) {
                return
            }
            val spaces = "  ".repeat(level)
            rootNode.valueList.forEach {
                //Log.d("ddw", "${spaces}>>comment: ${it.value.body}")
                callback.invoke(level, it.value.body)
                traverse(it.value.replies.value, level + 1, callback)
            }
        }
    }
}
