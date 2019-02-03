package com.worker8.redditapi.model.t1_comment.data

import com.google.gson.annotations.SerializedName
import com.worker8.redditapi.model.poko.Gildings
import com.worker8.redditapi.model.t1_comment.response.RedditReplyListingObject

sealed class RedditCommentDynamicData {
    data class TMore(
        val children: List<String> = listOf(),
        val count: Int = 0,
        val depth: Int = 0,
        val id: String = "",
        val name: String = "",
        val parent_id: String = ""
    ) : RedditCommentDynamicData()

    data class T1RedditCommentData(
        val archived: Boolean = false,
        val author_flair_background_color: String = "",
        val author: String = "",
        val author_flair_type: String = "",
        val author_fullname: String = "",
        val author_patreon_flair: Boolean = false,
        val body: String = "",
        val body_html: String = "",
        val can_gild: Boolean = false,
        val can_mod_post: Boolean = false,
        val collapsed: Boolean = false,
        val controversiality: Int = 0,
        val created: Int = 0,
        val created_utc: Int = 0,
        val depth: Int = 0,
        val downs: Int = 0,
        val gilded: Int = 0,
        val gildings: Gildings = Gildings(),
        val id: String = "",
        val is_submitter: Boolean = false,
        val link_id: String = "",
        val name: String = "",
        val no_follow: Boolean = false,
        val parent_id: String = "",
        val permalink: String = "",
        @SerializedName("replies")
        val replies: RedditReplyListingObject = RedditReplyListingObject(),
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
//    val approved_at_utc: Any,
//    val approved_by: Any,
//    val author_flair_css_class: Any,
//    val author_flair_richtext: List<Any>,
//    val author_flair_template_id: Any,
//    val author_flair_text: Any,
//    val author_flair_text_color: Any,
//    val banned_at_utc: Any,
//    val banned_by: Any,
//    val collapsed_reason: Any,
//    val distinguished: Any,
//    val edited: Boolean = false,
//    val likes: Any,
//    val mod_note: Any,
//    val mod_reason_by: Any,
//    val mod_reason_title: Any,
//    val mod_reports: List<Any>,
//    val num_reports: Any,
//    val removal_reason: Any,
//    val report_reasons: Any,
//    val user_reports: List<Any>
    ) : RedditCommentDynamicData()
}
