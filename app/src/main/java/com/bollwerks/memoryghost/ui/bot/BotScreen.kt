package com.bollwerks.memoryghost.ui.bot

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.bollwerks.memoryghost.utils.paddingSmall
import com.bollwerks.memoryghost.utils.spacedBySmall

@Composable
fun BotScreen(
    viewModel: BotModel,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBySmall(),
        modifier = Modifier
            .paddingSmall()
            .fillMaxSize(),
    ) {
        Row() {
            OutlinedTextField(
                value = uiState.prompt,
                onValueChange = viewModel::onPromptChange,
            )
            Button(
                onClick = viewModel::onSend,
            ) {
                Text("Send")
            }
        }
        Text(uiState.response)
    }
}