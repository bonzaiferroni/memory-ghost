package com.bollwerks.memoryghost

import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bollwerks.eznav.model.DrawerLinkConfig
import com.bollwerks.eznav.model.EzConfig
import com.bollwerks.eznav.model.ScaffoldConfig
import com.bollwerks.eznav.model.ScreenConfig
import com.bollwerks.memoryghost.data.AppDatabase
import com.bollwerks.memoryghost.data.DaoRepository
import com.bollwerks.memoryghost.ui.bot.BotModel
import com.bollwerks.memoryghost.ui.bot.BotScreen
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeModel
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeScreen
import com.bollwerks.memoryghost.ui.sandbox.SandboxScreen
import com.bollwerks.memoryghost.ui.study.StudyModel
import com.bollwerks.memoryghost.ui.study.StudyScreen

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
            initializer {
                StudyModel(dataRepository)
            }
            initializer {
                BotModel()
            }
        }
    },
    screens = listOf(
        ScreenConfig(
            route = AppRoutes.Home,
            drawerLink = DrawerLinkConfig(AppRoutes.Home, "Home", "🏠"),
            content = { _, _, _ ->
                Text("Hello home!")
            },
            scaffold = ScaffoldConfig(
                title = "Home"
            ),
        ),
        ScreenConfig(
            route = AppRoutes.Neuron,
            drawerLink = DrawerLinkConfig(AppRoutes.Neuron, "Neuron", "🧠"),
            content = { navController, drawerState, vmFactory ->
                NeuronTreeScreen(
                    drawerState = drawerState,
                    navController = navController,
                    viewModel = viewModel(factory = vmFactory),
                )
            },
        ),
        ScreenConfig(
            route = AppRoutes.Study,
            drawerLink = DrawerLinkConfig(AppRoutes.Study, "Study", "📝"),
            content = { _, _, vmFactory ->
                StudyScreen(
                    viewModel = viewModel(factory = vmFactory),
                )
            },
            scaffold = ScaffoldConfig(
                title = "Study"
            ),
        ),
        ScreenConfig(
            route = AppRoutes.Sandbox,
            drawerLink = DrawerLinkConfig(AppRoutes.Sandbox, "Sandbox", "🦦"),
            content = { _, drawerState, _ ->
                SandboxScreen(
                    drawerState = drawerState,
                )
            }
        ),
        ScreenConfig(
            route = AppRoutes.Bot,
            isDefaultRoute = true,
            drawerLink = DrawerLinkConfig(AppRoutes.Bot, "Bot", "🤖"),
            content = { _, _, vmFactory ->
                BotScreen(
                    viewModel = viewModel(factory = vmFactory)
                )
            },
            scaffold = ScaffoldConfig(
                title = "Study"
            ),
        )
    )
)