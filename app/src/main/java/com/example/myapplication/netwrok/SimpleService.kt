package com.example.myapplication.netwrok

import com.example.myapplication.Categories
import com.example.myapplication.MealDescriptionArray
//import com.example.myapplication.MealDescriptionArray
import com.example.myapplication.MealList
import com.example.myapplication.controller.RandomMeal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SimpleService {
    @GET("api/json/v1/1/categories.php")
    suspend fun GetListCategory(): Response<Categories>
    @GET("api/json/v1/1/random.php")
    suspend fun GetRandomMeal():Response<RandomMeal>
    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<MealList>
   @GET("api/json/v1/1/search.php")
    suspend fun getMealByName(@Query("s") mealName: String): Response<MealDescriptionArray>

}


