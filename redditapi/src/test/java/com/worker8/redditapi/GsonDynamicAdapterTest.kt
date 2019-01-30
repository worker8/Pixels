package com.worker8.redditapi

import com.google.gson.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.reflect.Type


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class GsonDynamicAdapterTest {

    @Test
    fun gsonDynamicDeserialize_correct() {
        val jsonString = "{\"type\":\"horse\",\"animal\":{}}"
        val barnDeserializer = BarnDeserializer()
        val gson = GsonBuilder().registerTypeAdapter(Barn::class.java, barnDeserializer).create()

        val barn = gson.fromJson(jsonString, Barn::class.java)

        //assertEquals(barn.animal.legs, (2 + 2).toLong())
    }
}

class BarnDeserializer : JsonDeserializer<Barn> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Barn {
        val jsonObject: JsonObject = json.asJsonObject
        System.out.print("jsonObject = ${jsonObject.getAsJsonObject("type")}")
        return Barn(type = "horse", animal = Cow())
    }
}

class Barn(val type: String, val animal: Animal) {

}

open class Animal

class Horse(val legs: Int = 4) : Animal() {}

class Cow(val legs: Int = 10) : Animal() {}

