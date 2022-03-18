package com.example.frontend.retroAPI.api

import com.example.frontend.retroAPI.api.util.Constraints.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: simpleAPI by lazy {
        retrofit.create(simpleAPI::class.java)
    }
}