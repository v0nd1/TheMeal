package com.huggydugy.themeal.ui.presentation

import com.huggydugy.themeal.R

sealed class Screen (
    val route: String = "",
    val icon: Int = 0,
    val title: String = "",

    ){
    object Menu : Screen(
        route = "1",
        title = "Профиль",
        icon =  R.drawable.icon1
    )
    object Basket : Screen(
        route = "2",
        title = "Анализы",
        icon = R.drawable.icon2
    )
    object Account : Screen(
        route = "3",
        title = "Результаты",
        icon = R.drawable.icon3
    )
}