package com.worker8.redditapi.model.t1_comment.response

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.common.ofType
import com.worker8.redditapi.model.t1_comment.data.RedditCommentDynamicData
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData

sealed class RedditReplyDynamicObject {
    class T1_RedditObject(
        @SerializedName("data") val value: RedditCommentDynamicData.T1RedditCommentData = RedditCommentDynamicData.T1RedditCommentData(),
        @SerializedName("kind") val kind: String = ""
    ) : RedditReplyDynamicObject()

    class TM_RedditObject(
        @SerializedName("data") val value: RedditCommentDynamicData.TMore = RedditCommentDynamicData.TMore(),
        @SerializedName("kind") val kind: String = ""
    ) : RedditReplyDynamicObject()

    companion object {
        fun flattenComments(rootNode: RedditReplyListingData): MutableList<Pair<Int, RedditCommentDynamicData>> {
            val store: MutableList<Pair<Int, RedditCommentDynamicData>> = mutableListOf()
            traverse(rootNode, 0) { level, redditCommentData ->
                store.add(level to redditCommentData)
            }
            return store
        }

        fun traverse(rootNode: RedditReplyListingData, level: Int, callback: (Int, RedditCommentDynamicData) -> Unit = { _, _ -> }) {
            if (rootNode.valueList.isEmpty()) {
                return
            }
            val spaces = "  ".repeat(level)
            rootNode.valueList.forEach { _redditReply ->
                _redditReply.ofType<T1_RedditObject> {
                    callback.invoke(level, it.value)
                    traverse(it.value.replies.value, level + 1, callback)
                }

                _redditReply.ofType<TM_RedditObject> {
                    callback.invoke(level, it.value)
                }
            }
        }
    }
}
