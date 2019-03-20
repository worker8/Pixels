package com.worker8.redditapi.model.t3_link.deserializer

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.worker8.redditapi.api.Serializer.gson
import com.worker8.redditapi.model.t3_link.response.RedditLinkListingObject
import java.io.Reader

class RedditLinkListingObjectDeserializer : ResponseDeserializable<RedditLinkListingObject> {
    override fun deserialize(reader: Reader): RedditLinkListingObject {
        return gson.fromJson(reader, RedditLinkListingObject::class.java)
    }
}
