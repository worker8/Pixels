package com.worker8.redditapi.model.t1_comment

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.listing.RedditCommentDataType
import com.worker8.redditapi.ofType

public sealed class RedditReply() {
    class T1_RedditObject(@SerializedName("data") val value: RedditCommentDataType.RedditCommentData = RedditCommentDataType.RedditCommentData(), @SerializedName("kind") val kind: String = "") : RedditReply()
    class TM_RedditObject(@SerializedName("data") val value: RedditCommentDataType.TMore = RedditCommentDataType.TMore(), @SerializedName("kind") val kind: String = "") : RedditReply()

    companion object {
        fun flattenComments(rootNode: RedditReplyListingData): MutableList<Pair<Int, RedditCommentDataType>> {
            val store: MutableList<Pair<Int, RedditCommentDataType>> = mutableListOf()
            traverse(rootNode, 0) { level, redditCommentData ->
                store.add(level to redditCommentData)
            }
            return store
        }

        fun traverse(rootNode: RedditReplyListingData, level: Int, callback: (Int, RedditCommentDataType) -> Unit = { _, _ -> }) {
            if (rootNode.valueList.isEmpty()) {
                return
            }
            val spaces = "  ".repeat(level)
            rootNode.valueList.forEach { _redditReply ->
                //Log.d("ddw", "${spaces}>>comment: ${it.value.body}")
                _redditReply.ofType<T1_RedditObject> {
                    callback.invoke(level, it.value)
                    traverse(it.value.replies.value, level + 1, callback)
                }

                _redditReply.ofType<TM_RedditObject> {
                    callback.invoke(level, it.value)
                    //traverse(it.value.replies.value, level + 1, callback)
                }
            }
        }
    }
}
