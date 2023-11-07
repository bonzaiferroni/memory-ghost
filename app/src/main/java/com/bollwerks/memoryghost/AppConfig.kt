package com.bollwerks.memoryghost

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bollwerks.eznav.DrawerConfig
import com.bollwerks.eznav.DrawerItemConfig
import com.bollwerks.eznav.EzConfig
import com.bollwerks.eznav.NavComposableConfig
import com.bollwerks.eznav.NavHostConfig
import com.bollwerks.memoryghost.data.AppDatabase
import com.bollwerks.memoryghost.data.DaoRepository
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeModel
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeScreen
import com.bollwerks.memoryghost.ui.sandbox.SandboxScreen

val appConfig = EzConfig(

    drawerConfig = DrawerConfig(
        mainAppIcon = { painterResource(R.drawable.ic_launcher_foreground) },
        drawerItems = listOf(
            DrawerItemConfig(AppRoutes.Home, "Home", Icons.Filled.Home),
            DrawerItemConfig(AppRoutes.Neuron, "Neuron", Icons.Filled.Face),
            DrawerItemConfig(AppRoutes.Sandbox, "Sandbox", Icons.Filled.Build),
        )
    ),

    navHostConfig = NavHostConfig(
        initialRoute = AppRoutes.Neuron,
        vmFactoryBuilder = { ctx ->
            val appDatabase = AppDatabase.getDatabase(ctx)
            val dataRepository = DaoRepository(appDatabase.neuronDao())

            viewModelFactory {
                initializer {
                    val savedStateHandle = createSavedStateHandle()
                    NeuronTreeModel(savedStateHandle, dataRepository)
                }
            }
        },
        composableConfigs = listOf(
            NavComposableConfig(
                route = AppRoutes.Home.route,
                content = { _, _, _ ->
                    Text("Hello home!")
                }
            ),
            NavComposableConfig(
                route = AppRoutes.Neuron.route,
                content = { navController, drawerState, vmFactory ->
                    NeuronTreeScreen(
                        drawerState = drawerState,
                        navController = navController,
                        viewModel = viewModel(factory = vmFactory),
                    )
                }
            ),
            NavComposableConfig(
                route = AppRoutes.Sandbox.route,
                content = { _, drawerState, _ ->
                    SandboxScreen(
                        drawerState = drawerState,
                    )
                }
            ),
        )
    ),
)