package com.huggydugy.themeal.data.model.responses

import com.google.gson.annotations.SerializedName
import com.huggydugy.themeal.data.model.Meal

data class MealResponse (
    @SerializedName("meals") val meals: List<Meal>
)