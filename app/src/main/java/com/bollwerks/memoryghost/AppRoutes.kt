package com.bollwerks.memoryghost

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.bollwerks.memoryghost.utils.EzRoute
import com.bollwerks.memoryghost.utils.EzRouteParam

object AppRoutes {
    object Home : EzRoute("home")
    object Neuron : EzRoute(
        "neuron",
        EzRouteParam(RouteKeys.id, NavType.IntType)
    ) {
        fun navigate(navController: NavController?, id: Int) {
            navigate(navController, RouteKeys.id to id);
        }

        fun getId(savedStateHandle: SavedStateHandle): Int? {
            return savedStateHandle.get<Int>(RouteKeys.id)
        }
    }
}

object RouteKeys {
    const val id = "id"
}