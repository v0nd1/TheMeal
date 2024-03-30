package com.huggydugy.themeal.data.repository

import com.huggydugy.themeal.data.model.Category
import com.huggydugy.themeal.data.model.Meal
import com.huggydugy.themeal.data.remote.FoodApi
import javax.inject.Inject

class CategoryRepo @Inject constructor(
    private val foodApi: FoodApi
) {
    suspend fun getCategory(): List<Category> {
        val categoryResponse = foodApi.getCategories()
        return categoryResponse.categories
    }
}