package com.bollwerks.memoryghost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bollwerks.eznav.AppDestination
import com.bollwerks.eznav.EzNav
import com.bollwerks.eznav.NavComposableConfig
import com.bollwerks.eznav.NavHostConfig
import com.bollwerks.memoryghost.data.AppDatabase
import com.bollwerks.memoryghost.data.DaoRepository
import com.bollwerks.memoryghost.ui.neuron.NeuronTreeScreen
import com.bollwerks.memoryghost.ui.sandbox.SandboxScreen
import com.bollwerks.memoryghost.ui.theme.MemoryGhostTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val appDatabase = remember { AppDatabase.getDatabase(applicationContext) }
            val daoRepository = remember { DaoRepository(appDatabase.neuronDao()) }
            val vmProvider = remember { ViewModelProvider(daoRepository) }
            val destinations = arrayListOf(
                AppDestination(AppRoutes.Home, "Home", Icons.Filled.Home),
                AppDestination(AppRoutes.Neuron, "Neuron", Icons.Filled.Face),
                AppDestination(AppRoutes.Sandbox, "Sandbox", Icons.Filled.Build),
            )

            val navHostConfig = remember {
                NavHostConfig(
                    startDestination = AppRoutes.Neuron.route,
                    composableConfigs = listOf(
                        NavComposableConfig(
                            route = AppRoutes.Home.route,
                            content = { _, _ ->
                                Text("Hello home!")
                            }
                        ),
                        NavComposableConfig(
                            route = AppRoutes.Neuron.route,
                            content = { navController, drawerState ->
                                NeuronTreeScreen(
                                    drawerState = drawerState,
                                    navController = navController,
                                    viewModel = viewModel(factory = vmProvider.Factory),
                                )
                            }
                        ),
                        NavComposableConfig(
                            route = AppRoutes.Sandbox.route,
                            content = { _, drawerState ->
                                SandboxScreen(
                                    drawerState = drawerState,
                                )
                            }
                        ),
                    )
                )
            }

            MemoryGhostTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EzNav(
                        config = appConfig,
                        destinations = destinations,
                        navHostConfig = navHostConfig,
                    )
                }
            }
        }
    }
}