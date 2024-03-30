package com.huggydugy.themeal.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huggydugy.themeal.data.model.Category
import com.huggydugy.themeal.data.model.Meal
import com.huggydugy.themeal.data.repository.CategoryRepo
import com.huggydugy.themeal.data.repository.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepo: MealRepo,
    private val categoryRepo: CategoryRepo
) : ViewModel() {
    private val _stateMeal = MutableStateFlow<List<Meal>>(emptyList())
    private val _stateCategory = MutableStateFlow<List<Category>>(emptyList())
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    private val _selectedMeals = MutableStateFlow<List<Meal>>(emptyList())
    val stateMeal: StateFlow<List<Meal>> get() = _stateMeal
    val stateCategory: StateFlow<List<Category>> get() = _stateCategory
    val selectedCategory: StateFlow<Category?> get() = _selectedCategory
    val selectedMeals: StateFlow<List<Meal>> get() = _selectedMeals

    init {
        viewModelScope.launch {
            val meals = mealRepo.getMeal()
            val categories = categoryRepo.getCategory()
            _stateMeal.value = meals
            _stateCategory.value = categories
            _selectedCategory.value = categories.first()
        }
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
        viewModelScope.launch {
            val meals = mealRepo.getMealsByCategory(category.strCategory ?: "")
            _selectedMeals.value = meals
        }
    }

}