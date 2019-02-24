package com.worker8.redditapi

import com.worker8.redditapi.RedditApi.Companion.gson
import com.worker8.redditapi.model.t1_comment.data.RedditReplyListingData
import com.worker8.redditapi.model.t1_comment.response.RedditReplyDynamicObject
import org.junit.Test

class DynamicDeserializationTest {

    @Test
    fun deserialize() {
        val redditReply1 = gson.fromJson(listingRepliesString, RedditReplyListingData::class.java)
        val redditReply2 = gson.fromJson(moreRepliesString, RedditReplyListingData::class.java)
        assert(redditReply1.valueList[0] is RedditReplyDynamicObject.T1RedditObject)
        assert(redditReply1.valueList[1] is RedditReplyDynamicObject.TMRedditObject)
        assert(redditReply2.valueList[0] is RedditReplyDynamicObject.TMRedditObject)
    }

    private val listingRepliesString = """
            {
                "modhash": "lisnktzfh2129063285920a394a6ea0def86deeaeacbfec2f0",
                "dist": null,
                "children": [
                    {
                        "kind": "t1",
                        "data": {
                            "subreddit_id": "t5_2qh0u",
                            "approved_at_utc": null,
                            "mod_reason_by": null,
                            "banned_by": null,
                            "author_flair_type": "text",
                            "removal_reason": null,
                            "link_id": "t3_airv0b",
                            "author_flair_template_id": null,
                            "likes": null,
                            "no_follow": false,
                            "replies": {},
                            "user_reports": [],
                            "saved": false,
                            "id": "eeqryrc",
                            "banned_at_utc": null,
                            "mod_reason_title": null,
                            "gilded": 0,
                            "archived": false,
                            "report_reasons": null,
                            "author": "wisertime07",
                            "can_mod_post": false,
                            "created_utc": 1548214736,
                            "send_replies": true,
                            "parent_id": "t1_eeqphf3",
                            "score": 8,
                            "author_fullname": "t2_bjtnj",
                            "approved_by": null,
                            "controversiality": 0,
                            "body": "RIP Your Inbox",
                            "edited": false,
                            "author_flair_css_class": null,
                            "is_submitter": false,
                            "downs": 0,
                            "author_flair_richtext": [],
                            "author_patreon_flair": false,
                            "collapsed_reason": null,
                            "body_html": "&lt;div class=\"md\"&gt;&lt;p&gt;RIP Your Inbox&lt;/p&gt;\n&lt;/div&gt;",
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
                            "permalink": "/r/pics/comments/airv0b/i_won_a_switch_from_a_lunchables_sweepstakes_and/eeqryrc/",
                            "num_reports": null,
                            "name": "t1_eeqryrc",
                            "created": 1548243536,
                            "subreddit": "pics",
                            "author_flair_text": null,
                            "collapsed": false,
                            "subreddit_name_prefixed": "r/pics",
                            "ups": 8,
                            "depth": 3,
                            "author_flair_background_color": null,
                            "mod_reports": [],
                            "mod_note": null,
                            "distinguished": null
                        }
                    },
                    {
                        "kind": "more",
                        "data": {
                            "count": 5,
                            "name": "t1_eeqrxgx",
                            "id": "eeqrxgx",
                            "parent_id": "t1_eeqphf3",
                            "depth": 3,
                            "children": [
                                "eeqrxgx",
                                "eeqr6a2"
                            ]
                        }
                    }
                ],
                "after": null,
                "before": null
            }

    """.trimIndent()

    private val moreRepliesString = """
        {
            "modhash": "lisnktzfh2129063285920a394a6ea0def86deeaeacbfec2f0",
            "dist": null,
            "children": [
                {
                    "kind": "more",
                    "data": {
                        "count": 1,
                        "name": "t1_eeqvgka",
                        "id": "eeqvgka",
                        "parent_id": "t1_eeqt6nn",
                        "depth": 6,
                        "children": [
                            "eeqvgka"
                        ]
                    }
                }
            ],
            "after": null,
            "before": null
        }
    """.trimIndent()

}

