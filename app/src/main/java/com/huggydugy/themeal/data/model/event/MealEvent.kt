package com.huggydugy.themeal.data.model.event

sealed interface MealEvent {
    data object ShowDialog: MealEvent
    data object HideDialog: MealEvent

}