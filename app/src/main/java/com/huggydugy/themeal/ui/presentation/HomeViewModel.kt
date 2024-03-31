package com.huggydugy.themeal.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huggydugy.themeal.data.model.Category
import com.huggydugy.themeal.data.model.Meal
import com.huggydugy.themeal.data.model.MealState
import com.huggydugy.themeal.data.model.event.MealEvent
import com.huggydugy.themeal.data.repository.CategoryRepo
import com.huggydugy.themeal.data.repository.MealRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mealRepo: MealRepo,
    private val categoryRepo: CategoryRepo
) : ViewModel() {
    private val _stateCategory = MutableStateFlow<List<Category>>(emptyList())
    private val _selectedCategory = MutableStateFlow<Category?>(null)
    private val _selectedMeals = MutableStateFlow<List<Meal>>(emptyList())
    private val _isBottomSheetVisible = MutableStateFlow(false)
    private val _selectedMeal = MutableStateFlow<Meal?>(null)


    val stateCategory: StateFlow<List<Category>> get() = _stateCategory
    val selectedCategory: StateFlow<Category?> get() = _selectedCategory
    val selectedMeals: StateFlow<List<Meal>> get() = _selectedMeals
    val isBottomSheetVisible: StateFlow<Boolean> get() = _isBottomSheetVisible
    val selectedMeal: StateFlow<Meal?> get() = _selectedMeal

    init {
        viewModelScope.launch {
            val categories = categoryRepo.getCategory()
            _stateCategory.value = categories
            _selectedCategory.value = categories.first()
            _selectedMeals.value = mealRepo.getMealsByCategory("Beef")
        }
    }

    fun onMealCardClicked(mealId: String) {
        viewModelScope.launch {
            val meal =  mealRepo.getMealById(mealId)
            _selectedMeal.value = meal
            onEvent(MealEvent.ShowDialog)
        }

    }

    fun onEvent(event: MealEvent){
        when(event){
            is MealEvent.ShowDialog -> {
                showBottomSheet()
            }
            is MealEvent.HideDialog -> {
               hideBottomSheet()
            }
        }
    }

    private fun showBottomSheet() {
        _isBottomSheetVisible.value = true
    }

    private fun hideBottomSheet() {
        _isBottomSheetVisible.value = false
    }

    fun onCategorySelected(category: Category) {
        _selectedCategory.value = category
        viewModelScope.launch {
            val meals = mealRepo.getMealsByCategory(category.strCategory ?: "")
            _selectedMeals.value = meals
        }
    }

}