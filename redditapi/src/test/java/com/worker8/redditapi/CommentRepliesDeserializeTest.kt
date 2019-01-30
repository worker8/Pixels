package com.worker8.redditapi

import com.google.gson.GsonBuilder
import com.worker8.redditapi.model.t1_comment.*
import com.worker8.redditapi.model.t3_link.RedditLinkListingData
import org.junit.Test


class CommentRepliesDeserializeTest {

    @Test
    fun deserialize() {
        val gson = GsonBuilder()
            .registerTypeAdapter(RedditCommentListingObject::class.java, RedditCommentListingObjectDeserializer())
            .registerTypeAdapter(RedditReplyListingData::class.java, RedditReplyListingDataDeserializer())
            .create()

        val result = gson.fromJson(commentReplyJson, RedditCommentListingObject::class.java)
        //RedditCommentDeserializer

        assert(true)
    }

    private val commentReplyJson = """
{
    "kind": "Listing",
    "data": {
        "modhash": "tbr0pc359j90ada02e996c5e192ccf40ffc098704a5c361448",
        "dist": null,
        "children": [
            {
                "kind": "t1",
                "data": {
                    "subreddit_id": "t5_324zi",
                    "approved_at_utc": null,
                    "mod_reason_by": null,
                    "banned_by": null,
                    "author_flair_type": "text",
                    "removal_reason": null,
                    "link_id": "t3_akxp7c",
                    "author_flair_template_id": null,
                    "likes": null,
                    "no_follow": true,
                    "replies": {
                        "kind": "Listing",
                        "data": {
                            "modhash": "tbr0pc359j90ada02e996c5e192ccf40ffc098704a5c361448",
                            "dist": null,
                            "children": [
                                {
                                    "kind": "t1",
                                    "data": {
                                        "subreddit_id": "t5_324zi",
                                        "approved_at_utc": null,
                                        "mod_reason_by": null,
                                        "banned_by": null,
                                        "author_flair_type": "text",
                                        "removal_reason": null,
                                        "link_id": "t3_akxp7c",
                                        "author_flair_template_id": null,
                                        "likes": null,
                                        "no_follow": true,
                                        "replies": "",
                                        "user_reports": [],
                                        "saved": false,
                                        "id": "ef8tvm9",
                                        "banned_at_utc": null,
                                        "mod_reason_title": null,
                                        "gilded": 0,
                                        "archived": false,
                                        "report_reasons": null,
                                        "author": "Spallboy",
                                        "can_mod_post": false,
                                        "created_utc": 1548748560,
                                        "send_replies": true,
                                        "parent_id": "t1_ef8t7xb",
                                        "score": 3,
                                        "author_fullname": "t2_10i2zu",
                                        "approved_by": null,
                                        "controversiality": 0,
                                        "body": "It's crazy how nature do that",
                                        "edited": false,
                                        "author_flair_css_class": null,
                                        "is_submitter": false,
                                        "downs": 0,
                                        "author_flair_richtext": [],
                                        "author_patreon_flair": false,
                                        "collapsed_reason": null,
                                        "body_html": "&lt;div class=\"md\"&gt;&lt;p&gt;It&amp;#39;s crazy how nature do that&lt;/p&gt;\n&lt;/div&gt;",
                                        "stickied": false,
                                        "subreddit_type": "public",
                                        "can_gild": true,
                                        "gildings": {
                                            "gid_1": 0,
                                            "gid_2": 0,
                                            "gid_3": 0
                                        },
                                        "author_flair_text_color": null,
                                        "score_hidden": false,
                                        "permalink": "/r/natureismetal/comments/akxp7c/hedgehog_skeleton/ef8tvm9/",
                                        "num_reports": null,
                                        "name": "t1_ef8tvm9",
                                        "created": 1548777360,
                                        "subreddit": "natureismetal",
                                        "author_flair_text": null,
                                        "collapsed": false,
                                        "subreddit_name_prefixed": "r/natureismetal",
                                        "ups": 3,
                                        "depth": 1,
                                        "author_flair_background_color": null,
                                        "mod_reports": [],
                                        "mod_note": null,
                                        "distinguished": null
                                    }
                                },
                                {
                                    "kind": "more",
                                    "data": {
                                        "count": 2,
                                        "name": "t1_ef5zcnn",
                                        "id": "ef5zcnn",
                                        "parent_id": "t1_ef58ks4",
                                        "depth": 5,
                                        "children": [
                                            "ef5zcnn"
                                        ]
                                    }
                                }
                            ],
                            "after": null,
                            "before": null
                        }
                    },
                    "user_reports": [],
                    "saved": false,
                    "id": "ef8t7xb",
                    "banned_at_utc": null,
                    "mod_reason_title": null,
                    "gilded": 0,
                    "archived": false,
                    "report_reasons": null,
                    "author": "YellowOnline",
                    "can_mod_post": false,
                    "created_utc": 1548747764,
                    "send_replies": true,
                    "parent_id": "t3_akxp7c",
                    "score": 0,
                    "author_fullname": "t2_1gywsy",
                    "approved_by": null,
                    "controversiality": 0,
                    "body": "This is clearly cleaned by a human ",
                    "edited": false,
                    "author_flair_css_class": null,
                    "is_submitter": false,
                    "downs": 0,
                    "author_flair_richtext": [],
                    "author_patreon_flair": false,
                    "collapsed_reason": null,
                    "body_html": "&lt;div class=\"md\"&gt;&lt;p&gt;This is clearly cleaned by a human &lt;/p&gt;\n&lt;/div&gt;",
                    "stickied": false,
                    "subreddit_type": "public",
                    "can_gild": true,
                    "gildings": {
                        "gid_1": 0,
                        "gid_2": 0,
                        "gid_3": 0
                    },
                    "author_flair_text_color": null,
                    "score_hidden": false,
                    "permalink": "/r/natureismetal/comments/akxp7c/hedgehog_skeleton/ef8t7xb/",
                    "num_reports": null,
                    "name": "t1_ef8t7xb",
                    "created": 1548776564,
                    "subreddit": "natureismetal",
                    "author_flair_text": null,
                    "collapsed": false,
                    "subreddit_name_prefixed": "r/natureismetal",
                    "ups": 0,
                    "depth": 0,
                    "author_flair_background_color": null,
                    "mod_reports": [],
                    "mod_note": null,
                    "distinguished": null
                }
            }
        ],
        "after": null,
        "before": null
    }
}
    """.trimIndent()

}

