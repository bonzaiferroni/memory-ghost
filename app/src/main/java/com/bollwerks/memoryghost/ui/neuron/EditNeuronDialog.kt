package com.bollwerks.memoryghost.ui.neuron

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bollwerks.memoryghost.ui.common.AcceptCancelButtons
import com.bollwerks.memoryghost.ui.common.AppDialog
import com.bollwerks.memoryghost.ui.common.ValueField
import com.bollwerks.memoryghost.utils.ezlisten.ListenInputField
import com.bollwerks.memoryghost.utils.paddingSmall

@Composable
fun EditNeuronDialog(
    viewModel: NeuronTreeModel,
    uiState: NeuronTreeState
) {
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
            ListenInputField(
                label = "Name",
                value = uiState.editNeuronName,
                onValueChange = viewModel::editNeuronName,
            )
            ListenInputField(
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
}