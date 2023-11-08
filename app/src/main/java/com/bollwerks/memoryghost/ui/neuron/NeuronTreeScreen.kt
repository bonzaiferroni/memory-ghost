package com.bollwerks.memoryghost.ui.neuron

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.bollwerks.eznav.EzScaffold
import com.bollwerks.eznav.ScreenMenuItem
import com.bollwerks.eznav.model.FabConfig
import com.bollwerks.memoryghost.AppRoutes
import com.bollwerks.memoryghost.RouteKeys
import com.bollwerks.memoryghost.data.SampleRepository
import com.bollwerks.memoryghost.ui.common.AcceptCancelButtons
import com.bollwerks.memoryghost.ui.common.AppDialog
import com.bollwerks.memoryghost.ui.common.MgIconButton
import com.bollwerks.memoryghost.ui.common.RevealBox
import com.bollwerks.memoryghost.ui.common.ValueField
import com.bollwerks.memoryghost.ui.theme.MemoryGhostTheme
import com.bollwerks.memoryghost.utils.Gaps
import com.bollwerks.memoryghost.utils.Paddings
import com.bollwerks.memoryghost.utils.ezspeak.SpeechInputField
import com.bollwerks.memoryghost.utils.paddingSmall

@Composable
fun NeuronTreeScreen(
    drawerState: DrawerState,
    navController: NavController?,
    viewModel: NeuronTreeModel,
    modifier: Modifier = Modifier,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    AppDialog(
        showDialog = uiState.showEditNeuronDialog,
    ) {
        Column(modifier = Modifier.paddingSmall()) {
            ValueField(
                label = "Parent",
                value = if (uiState.isNewNeuron) uiState.neuron?.name ?: "(root)"
                else uiState.parent?.name ?: "(root)",
                onValueChange = {},
            )
            SpeechInputField(
                label = "Name",
                value = uiState.editNeuronName,
                onValueChange = viewModel::editNeuronName,
            )
            SpeechInputField(
                label = "Value (optional)",
                value = uiState.editNeuronValue,
                onValueChange = viewModel::editNeuronValue,
            )
            AcceptCancelButtons(
                onAccept = viewModel::acceptEditNeuron,
                onCancel = viewModel::cancelEditNeuron,
                enabled = uiState.isValidEditNeuron,
            )
        }
    }

    EzScaffold(
        modifier = modifier,
        drawerState = drawerState,
        title = "🌳",
        fabConfig = FabConfig(
            icon = Icons.Filled.Add,
            onClick = viewModel::addNeuron,
            contentDescription = "Add child neuron",
        ),
        menuItems = listOf(
            ScreenMenuItem(
                name = "Export",
                onClick = {
                    viewModel.exportTree(context)
                },
            ),
            ScreenMenuItem(
                name = "Import",
                onClick = {
                    viewModel.importTree(context)
                },
            ),
        )
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
                        Box() {
                            Text(
                                text = neuron.name,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .fillMaxWidth(),
                            )
                            MgIconButton(
                                icon = Icons.Filled.Edit,
                                modifier = Modifier.align(Alignment.CenterEnd),
                                onClick = viewModel::editNeuron,
                            )
                        }
                        neuron.answer?.let { value ->
                            RevealBox() {
                                Text(
                                    text = value,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth(),
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