package com.bollwerks.memoryghost.ui.neuron

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.bollwerks.memoryghost.AppRoutes
import com.bollwerks.memoryghost.RouteKeys
import com.bollwerks.memoryghost.data.SampleRepository
import com.bollwerks.memoryghost.ui.common.AppScaffold
import com.bollwerks.memoryghost.ui.common.FabParams
import com.bollwerks.memoryghost.ui.common.RevealBox
import com.bollwerks.memoryghost.ui.theme.MemoryGhostTheme
import com.bollwerks.memoryghost.utils.Gaps
import com.bollwerks.memoryghost.utils.Paddings

@Composable
fun NeuronTreeScreen(
    drawerState: DrawerState,
    navController: NavController?,
    viewModel: NeuronTreeModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()

    AppScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = "\uD83C\uDF33",
        fabParams = FabParams(
            icon = Icons.Filled.Add,
            onClick = viewModel::addNeuron,
            contentDescription = "Add child neuron",
        ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Paddings.small()),
            verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            uiState.neuron?.let { neuron ->
                OtherNeuronCard(
                    name = uiState.parent?.name ?: "\uD83C\uDF33",
                ) {
                    AppRoutes.Neuron.navigate(navController, uiState.parent?.id)
                }
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(Paddings.small()),
                        verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
                    ) {
                        Text(
                            text = neuron.name,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.fillMaxWidth(),
                        )
                        neuron.value?.let { value ->
                            RevealBox() {
                                Text(
                                    text = value,
                                )
                            }
                        }
                    }
                }
            }

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Gaps.medium()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(uiState.children) { neuron ->
                    OtherNeuronCard(
                        name = neuron.name,
                    ) {
                        AppRoutes.Neuron.navigate(navController, neuron.id)
                    }
                }
            }
        }
    }
}

@Composable
fun OtherNeuronCard(
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        modifier = modifier.clickable { onClick() },
    ) {
        Row(modifier = Modifier.padding(Paddings.small())) {
            Text(text = name)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun NeuronTreeScreenPreview() {
    MemoryGhostTheme {
        var savedStateHandle = SavedStateHandle()
        savedStateHandle[RouteKeys.id] = 2
        NeuronTreeScreen(
            drawerState = DrawerState(DrawerValue.Closed),
            navController = null,
            viewModel = NeuronTreeModel(savedStateHandle, SampleRepository()),
        )
    }
}