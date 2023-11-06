package com.bollwerks.memoryghost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bollwerks.eznav.AppDestination
import com.bollwerks.eznav.AppDrawer
import com.bollwerks.eznav.DrawerConfig
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
            val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            val navController: NavHostController = rememberNavController()
            val navHostConfig = remember {
                NavHostConfig(
                    startDestination = AppRoutes.Neuron.route,
                    composableConfigs = listOf(
                        NavComposableConfig(
                            route = AppRoutes.Home.route,
                            content = {
                                Text("Hello home!")
                            }
                        ),
                        NavComposableConfig(
                            route = AppRoutes.Neuron.route,
                            content = {
                                NeuronTreeScreen(
                                    drawerState = drawerState,
                                    navController = navController,
                                    viewModel = viewModel(factory = vmProvider.Factory),
                                )
                            }
                        ),
                        NavComposableConfig(
                            route = AppRoutes.Sandbox.route,
                            content = {
                                SandboxScreen(
                                    drawerState = drawerState,
                                )
                            }
                        ),
                    )
                )
            }
            val mainAppIcon = painterResource(R.drawable.ic_launcher_foreground)
            val drawerConfig = remember {
                DrawerConfig(
                    defaultPick = AppRoutes.Home,
                    mainAppIcon = mainAppIcon,
                )
            }

            MemoryGhostTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppDrawer(
                        destinations = destinations,
                        drawerConfig = drawerConfig,
                        navHostConfig = navHostConfig,
                        drawerState = drawerState,
                    )
                }
            }
        }
    }
}