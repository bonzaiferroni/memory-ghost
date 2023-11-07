package com.bollwerks.memoryghost

import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bollwerks.eznav.model.DrawerLinkConfig
import com.bollwerks.eznav.model.EzConfig
import com.bollwerks.eznav.model.ScreenConfig
import com.bollwerks.memoryghost.data.AppDatabase
import com.bollwerks.memoryghost.data.DaoRepository
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeModel
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeScreen
import com.bollwerks.memoryghost.ui.sandbox.SandboxScreen

val appConfig = EzConfig(

    mainAppIcon = { painterResource(R.drawable.ic_launcher_foreground) },

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
    screens = listOf(
        ScreenConfig(
            route = AppRoutes.Home,
            drawerLink = DrawerLinkConfig(AppRoutes.Home, "Home", "🏠"),
            content = { _, _, _ ->
                Text("Hello home!")
            }
        ),
        ScreenConfig(
            route = AppRoutes.Neuron,
            isDefaultRoute = true,
            drawerLink = DrawerLinkConfig(AppRoutes.Neuron, "Neuron", "🧠"),
            content = { navController, drawerState, vmFactory ->
                NeuronTreeScreen(
                    drawerState = drawerState,
                    navController = navController,
                    viewModel = viewModel(factory = vmFactory),
                )
            }
        ),
        ScreenConfig(
            route = AppRoutes.Sandbox,
            drawerLink = DrawerLinkConfig(AppRoutes.Sandbox, "Sandbox", "\uD83E\uDDA6"),
            content = { _, drawerState, _ ->
                SandboxScreen(
                    drawerState = drawerState,
                )
            }
        ),
    )
)