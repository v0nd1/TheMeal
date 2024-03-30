package com.huggydugy.themeal.data.model.responses

import com.google.gson.annotations.SerializedName
import com.huggydugy.themeal.data.model.Category

data class CategoryResponse(
    @SerializedName("categories") val categories: List<Category>
)
