package com.worker8.redditapi.model.listing

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.Gildings
import com.worker8.redditapi.model.t1_comment.RedditReply
import com.worker8.redditapi.model.t1_comment.RedditReplyListingObject

interface RedditObject<T> {
    val value: T
    val kind: String
}

sealed class RedditCommentDataType {
    data class TMore(
        val children: List<String> = listOf(),
        val count: Int = 0,
        val depth: Int = 0,
        val id: String = "",
        val name: String = "",
        val parent_id: String = ""
    ) : RedditCommentDataType()


    data class RedditCommentData(
//    val approved_at_utc: Any,
//    val approved_by: Any,
        val archived: Boolean = false,
        val author_flair_background_color: String = "",
        val author: String = "",
//    val author_flair_css_class: Any,
//    val author_flair_richtext: List<Any>,
//    val author_flair_template_id: Any,
//    val author_flair_text: Any,
//    val author_flair_text_color: Any,
        val author_flair_type: String = "",
        val author_fullname: String = "",
        val author_patreon_flair: Boolean = false,
//    val banned_at_utc: Any,
//    val banned_by: Any,
        val body: String = "",
        val body_html: String = "",
        val can_gild: Boolean = false,
        val can_mod_post: Boolean = false,
        val collapsed: Boolean = false,
//    val collapsed_reason: Any,
        val controversiality: Int = 0,
        val created: Int = 0,
        val created_utc: Int = 0,
        val depth: Int = 0,
//    val distinguished: Any,
        val downs: Int = 0,
        //val edited: Boolean = false,
        val gilded: Int = 0,
        val gildings: Gildings = Gildings(),
        val id: String = "",
        val is_submitter: Boolean = false,
//    val likes: Any,
        val link_id: String = "",
//    val mod_note: Any,
//    val mod_reason_by: Any,
//    val mod_reason_title: Any,
//    val mod_reports: List<Any>,
        val name: String = "",
        val no_follow: Boolean = false,
//    val num_reports: Any,
        val parent_id: String = "",
        val permalink: String = "",
//    val removal_reason: Any,
        @SerializedName("replies")
        val replies: RedditReplyListingObject = RedditReplyListingObject(),
//    val report_reasons: Any,
        val saved: Boolean = false,
        val score: Int = 0,
        val score_hidden: Boolean = false,
        val send_replies: Boolean = false,
        val stickied: Boolean = false,
        val subreddit: String = "",
        val subreddit_id: String = "",
        val subreddit_name_prefixed: String = "",
        val subreddit_type: String = "",
        val ups: Int = 0
//    val user_reports: List<Any>
    ) : RedditCommentDataType()
}


