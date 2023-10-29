package com.bollwerks.memoryghost.ui.neuron

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.bollwerks.memoryghost.ui.common.AppScaffold
import com.bollwerks.memoryghost.ui.common.FabParams
import com.bollwerks.memoryghost.utils.Gaps
import com.bollwerks.memoryghost.utils.Paddings

@Composable
fun NeuronListScreen(
    drawerState: DrawerState,
    navController: NavController?,
    viewModel: NeuronListModel,
    modifier: Modifier = Modifier,
) {
    AppScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = "Neurons",
        fabParams = FabParams(
            icon = Icons.Filled.Add,
            onClick = viewModel::addNeuron,
            contentDescription = "Add neuron",
        ),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = Paddings.small()),
            verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
        ) {
            Text(text = "hello neuron")
        }
    }
}