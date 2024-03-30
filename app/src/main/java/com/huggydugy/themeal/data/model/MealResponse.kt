package com.huggydugy.themeal.data.model

import com.google.gson.annotations.SerializedName

data class MealResponse (
    @SerializedName("meals") val meals: List<Meal>
)