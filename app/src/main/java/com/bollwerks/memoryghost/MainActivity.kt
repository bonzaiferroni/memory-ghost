package com.bollwerks.memoryghost

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
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

            MemoryGhostTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    EzNav(
                        config = appConfig,
                    )
                }
            }
        }
    }
}