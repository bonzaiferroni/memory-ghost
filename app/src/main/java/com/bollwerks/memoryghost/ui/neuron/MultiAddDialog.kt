package com.bollwerks.memoryghost.ui.neuron

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bollwerks.memoryghost.ui.common.AcceptCancelButtons
import com.bollwerks.memoryghost.ui.common.AppDialog
import com.bollwerks.memoryghost.utils.paddingSmall

@Composable
fun MultiAddDialog(
    viewModel: NeuronTreeModel,
    uiState: NeuronTreeState
) {
    AppDialog(
        showDialog = uiState.showMultiAddDialog,
    ) {
        Column(
            modifier = Modifier
                .paddingSmall()
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = uiState.multiAddText,
                onValueChange = viewModel::onMultiAddChange,
                label = { Text("Multi Add") },
            )
            AcceptCancelButtons(
                onAccept = viewModel::acceptMultiAdd,
                onCancel = viewModel::cancelMultiAdd,
                enabled = uiState.multiAddText.isNotBlank(),
            )
        }
    }
}