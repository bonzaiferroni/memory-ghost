package com.bollwerks.memoryghost

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.bollwerks.eznav.EzRoute
import com.bollwerks.eznav.EzRouteParam

object AppRoutes {
    object Home : EzRoute("home")
    object Neuron : EzRoute(
        "neuron",
        EzRouteParam(RouteKeys.id, NavType.IntType, defaultValue = 0)
    ) {
        fun navigate(navController: NavController?, id: Int? = null) {
            navigate(navController, RouteKeys.id to (id ?: 0))
        }

        fun getId(savedStateHandle: SavedStateHandle): Int? {
            return savedStateHandle.get<Int>(RouteKeys.id)
        }
    }
    object Sandbox : EzRoute("sandbox")
}

object RouteKeys {
    const val id = "id"
}