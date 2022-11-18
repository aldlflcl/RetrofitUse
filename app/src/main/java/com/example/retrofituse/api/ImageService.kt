package com.example.retrofituse.api

import com.example.retrofituse.BuildConfig
import com.squareup.moshi.*
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com/"

private val moshi = Moshi.Builder()
    .add(NullToEmptyStringAdapter)
    .add(UserNameAdapter)
    .addLast(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface ImageService {
    @GET("/photos")
    @Headers(
        "Authorization: Client-ID ${BuildConfig.SPLASH_API_KEY}"
    )
    suspend fun getImages(@Query("per_page") count: Int): List<Image>
}

object ImageApi {
    val retrofitService: ImageService by lazy {
        retrofit.create(ImageService::class.java)
    }
}

private object NullToEmptyStringAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): String {
        if (reader.peek() != JsonReader.Token.NULL) return reader.nextString()
        reader.nextNull<Unit>()
        return ""
    }
}

private object UserNameAdapter {
    @FromJson
    @UserName
    fun fromJson(reader: JsonReader): String {
        var value = ""
        reader.beginObject()
        while (reader.hasNext()) {
            if (reader.nextName() == "username") {
                value = reader.nextString()
            } else {
                reader.skipValue()
            }
        }
        reader.endObject()
        return value
    }

    @ToJson
    fun toJson(@UserName userName: String): String {
        return userName
    }
}

@JsonQualifier
annotation class UserName