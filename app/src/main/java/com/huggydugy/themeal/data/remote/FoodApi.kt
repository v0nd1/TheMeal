package com.huggydugy.themeal.data.remote

import com.huggydugy.themeal.data.model.responses.CategoryResponse
import com.huggydugy.themeal.data.model.responses.MealResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {
    @GET("search.php?s")
    suspend fun getMeal(): MealResponse

    @GET("lookup.php")
    suspend fun getMealById(
        @Query("i") id: String
    ): MealResponse

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getMealsByCategories(
        @Query("c") category: String
    ): MealResponse

}