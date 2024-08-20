package com.example.myapplication.netwrok

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetroBuilder {

   companion object{ //IMPLEMENTS STATIC
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.themealdb.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service /*ref*/= retrofit.create(SimpleService::class.java)

    }
}