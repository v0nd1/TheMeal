package com.huggydugy.themeal.data.model

data class MealState(
    val id: String = "",
    val mealName: String = "",
    val drinkAlternate: String? = null,
    val category: String? = null,
    val area: String? = null,
    val instructions: String? = null,
    val mealThumb: String? = null,
    val tags: String? = null,
    val youtube: String? = null,
    val ingredients: List<String?> = emptyList(),
    val measures: List<String?> = emptyList(),
    val source: String? = null,
    val imageSource: String? = null,
    val creativeCommonsConfirmed: String? = null,
    val dateModified: String? = null,
    val isShow: Boolean = false
) {
    constructor(meal: Meal) : this(
        id = meal.idMeal ?: "",
        mealName = meal.strMeal,
        drinkAlternate = meal.strDrinkAlternate,
        category = meal.strCategory,
        area = meal.strArea,
        instructions = meal.strInstructions,
        mealThumb = meal.strMealThumb,
        tags = meal.strTags,
        youtube = meal.strYoutube,
        ingredients = listOf(
            meal.strIngredient1, meal.strIngredient2, meal.strIngredient3, meal.strIngredient4,
            meal.strIngredient5, meal.strIngredient6, meal.strIngredient7, meal.strIngredient8,
            meal.strIngredient9, meal.strIngredient10, meal.strIngredient11, meal.strIngredient12,
            meal.strIngredient13, meal.strIngredient14, meal.strIngredient15, meal.strIngredient16,
            meal.strIngredient17, meal.strIngredient18, meal.strIngredient19, meal.strIngredient20
        ),
        measures = listOf(
            meal.strMeasure1, meal.strMeasure2, meal.strMeasure3, meal.strMeasure4,
            meal.strMeasure5, meal.strMeasure6, meal.strMeasure7, meal.strMeasure8,
            meal.strMeasure9, meal.strMeasure10, meal.strMeasure11, meal.strMeasure12,
            meal.strMeasure13, meal.strMeasure14, meal.strMeasure15, meal.strMeasure16,
            meal.strMeasure17, meal.strMeasure18, meal.strMeasure19, meal.strMeasure20
        ),
        source = meal.strSource,
        imageSource = meal.strImageSource,
        creativeCommonsConfirmed = meal.strCreativeCommonsConfirmed,
        dateModified = meal.dateModified
    )
}
