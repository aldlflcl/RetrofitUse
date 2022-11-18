package com.example.retrofituse.api

import androidx.annotation.Nullable
import com.example.retrofituse.BuildConfig
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.addAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

private const val BASE_URL = "https://api.unsplash.com/"

private val moshi = Moshi.Builder()
    .add(NullToEmptyStringAdapter)
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
    suspend fun getImages(@Query("count") count: Int): List<Photo>
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

@JsonQualifier
annotation class NullToEmptyString