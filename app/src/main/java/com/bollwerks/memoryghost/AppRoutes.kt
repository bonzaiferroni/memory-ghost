package com.bollwerks.memoryghost

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavType
import com.bollwerks.eznav.EzRoute
import com.bollwerks.eznav.model.RouteParam

object AppRoutes {
    object Home : EzRoute("home")
    object Neuron : EzRoute(
        "neuron",
        RouteParam(RouteKeys.id, NavType.IntType, true, 0)
    ) {
        fun navigate(navController: NavController?, id: Int? = null) {
            navigate(navController, RouteKeys.id to (id ?: 0))
        }

        fun getId(savedStateHandle: SavedStateHandle): Int? {
            val value:Int? = savedStateHandle[RouteKeys.id]
//            return if (value is String) {
//                value.toInt()
//            } else {
//                value as? Int
//            }
            return value
        }
    }
    object Sandbox : EzRoute("sandbox")
}

object RouteKeys {
    const val id = "id"
}