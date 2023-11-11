package com.bollwerks.memoryghost.ui.bot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bollwerks.memoryghost.BuildConfig
import com.bollwerks.memoryghost.utils.ezbot.EzBot
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BotModel : ViewModel() {
    private val bot = EzBot(BuildConfig.OPENAI_API_KEY)
    private val _uiState = MutableStateFlow(BotState())
    private var state
        get() = _uiState.value
        set(value) { _uiState.value = value }
    val uiState = _uiState.asStateFlow()

    fun onPromptChange(prompt: String) {
        state = state.copy(prompt = prompt)
    }

    fun onSend() {
        viewModelScope.launch {
            val response = bot.makeRequest(state.prompt)
            state = state.copy(response = response)
        }
    }
}

data class BotState(
    val prompt: String = "",
    val response: String = ""
)