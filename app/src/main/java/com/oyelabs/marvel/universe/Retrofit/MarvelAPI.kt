package com.oyelabs.marvel.universe.Retrofit

import androidx.lifecycle.LiveData
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

val BASE_URL = ""

interface MarvelAPI {

    @GET("https://gateway.marvel.com:443/v1/public/characters")
    suspend fun getAllCharacters(
        @Query("offset") offset: Int,
        @Query("apikey") publicApiKey: String,
        @Query("hash") haskKey: String, // has key is cobinationof ts+privatekey+publickey
        @Query("ts") tsValue: String
    ):Response<Any>

    @GET("https://gateway.marvel.com:443/v1/public/characters")
    fun getSingleCharacters(
        @Query("nameStartsWith") characterStartingName: String,
        @Query("orderBy") orderBy: String = "name", // more documentation find on -> https://developer.marvel.com/documentation/authorization
        @Query("apikey") publicApiKey: String,
        @Query("hash") haskKey: String, // has key is cobinationof ts+privatekey+publickey
        @Query("ts") tsValue: String
    )


}