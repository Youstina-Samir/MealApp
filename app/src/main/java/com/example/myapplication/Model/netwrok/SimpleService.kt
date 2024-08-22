package com.example.myapplication.Model.netwrok

import com.example.myapplication.Model.Categories
import com.example.myapplication.Model.Countires
import com.example.myapplication.Model.MealDescriptionArray
//import com.example.myapplication.MealDescriptionArray
import com.example.myapplication.Model.MealList
import com.example.myapplication.Model.RandomMeal
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
    @GET("api/json/v1/1/list.php?a=list")
    suspend fun getAreaList(): Response<Countires>
    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByArea(@Query("a") area: String): Response<MealList>
    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByIngredient(@Query("i") ingredient: String): Response<MealList>


}


