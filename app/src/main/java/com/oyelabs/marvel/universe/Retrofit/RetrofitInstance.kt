package com.oyelabs.marvel.universe.Retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            Retrofit.Builder().baseUrl("https://gateway.marvel.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpClient()).build()
        }

        val marvelAPI: MarvelAPI by lazy {
            retrofit.create(MarvelAPI::class.java)
        }
    }
}