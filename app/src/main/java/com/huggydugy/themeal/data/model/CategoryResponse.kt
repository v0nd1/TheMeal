package com.huggydugy.themeal.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("categories") val categories: List<Category>
)
