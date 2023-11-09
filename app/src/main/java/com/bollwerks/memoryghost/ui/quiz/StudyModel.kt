package com.bollwerks.memoryghost.ui.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bollwerks.memoryghost.data.DataRepository
import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StudyModel(
    val dataRepository: DataRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(QuizState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }
    val uiState = _uiState.asStateFlow()

    private var neurons: List<Neuron> = emptyList()

    init {
        viewModelScope.launch {
            neurons = dataRepository.getAllNeurons().first().filter { it.answer != null }
        }
    }

    private var correctAnswer = ""

    fun startQuiz() {
        if (neurons.size < 4) {
            state = state.copy(message = "Not enough neurons")
            return
        }
        val neuron = neurons
            .filter { it.name != state.question }
            .shuffled().first()
        val answers = neurons
            .filter { it.id != neuron.id }
            .shuffled().take(3).map { it.answer!! } + neuron.answer!!
        correctAnswer = neuron.answer
        state = state.copy(
            message = "Next question...",
            question = neuron.name,
            answers = answers.shuffled(),
            isListening = !state.speak && state.listen
        )
    }

    fun onAnswer(answer: String) {
        val message = if (answer == correctAnswer) "Correct 🙌" else "Try again"
        state = state.copy(
            message = message,
            isListening = false,
        )
    }

    fun toggleListen() {
        state = state.copy(listen = !state.listen)
    }

    fun toggleSpeak() {
        state = state.copy(speak = !state.speak)
    }

    fun onDoneSpeaking() {
        state = state.copy(isListening = state.listen)
    }
}

data class QuizState(
    val question: String? = null,
    val answers: List<String> = emptyList(),
    val message: String = "Ready?",
    val listen: Boolean = true,
    val speak: Boolean = true,
    val isListening: Boolean = false,
)