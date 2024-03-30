package com.huggydugy.themeal.data.repository

import com.huggydugy.themeal.data.model.Meal
import com.huggydugy.themeal.data.remote.FoodApi
import javax.inject.Inject

class MealRepo @Inject constructor(
    private val foodApi: FoodApi
) {
    suspend fun getMeal(): List<Meal> {
        val mealResponse = foodApi.getMeal()
        return mealResponse.meals
    }

    suspend fun getMealsByCategory(category: String): List<Meal> {
        val mealResponse = foodApi.getMealsByCategories(category)
        return mealResponse.meals
    }
}