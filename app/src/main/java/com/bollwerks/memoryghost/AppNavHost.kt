package com.bollwerks.memoryghost

import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeScreen
import com.bollwerks.memoryghost.ui.sandbox.SandboxScreen

@Composable
fun AppNavHost(
    viewModelProvider: ViewModelProvider,
    navController: NavHostController,
    drawerState: DrawerState,
) {
    NavHost(
        navController = navController,
        startDestination = AppRoutes.Neuron.route
    ) {
        composable(route = AppRoutes.Home.route) {
            Text("Hello home!")
        }
        composable(
            route = AppRoutes.Neuron.route,
            arguments = AppRoutes.Neuron.navArguments
        ) {
            NeuronTreeScreen(
                drawerState = drawerState,
                navController = navController,
                viewModel = viewModel(factory = viewModelProvider.Factory),
            )
        }
        composable(route = AppRoutes.Sandbox.route) {
            SandboxScreen(
                drawerState = drawerState,
            )
        }
    }
}