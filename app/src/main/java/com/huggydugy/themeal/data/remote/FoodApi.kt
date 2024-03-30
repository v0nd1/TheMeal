package com.huggydugy.themeal.data.remote

import com.huggydugy.themeal.data.model.CategoryResponse
import com.huggydugy.themeal.data.model.Meal
import com.huggydugy.themeal.data.model.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("search.php?s")
    suspend fun getMeal(): MealResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategories(@Query("c") category: String): MealResponse

}