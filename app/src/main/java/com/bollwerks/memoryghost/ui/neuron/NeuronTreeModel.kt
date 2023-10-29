package com.bollwerks.memoryghost.ui.neuron

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bollwerks.memoryghost.AppRoutes
import com.bollwerks.memoryghost.data.DataRepository
import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NeuronTreeModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val neuronId: Int? = AppRoutes.Neuron.getId(savedStateHandle = savedStateHandle)

    private val _uiState = MutableStateFlow(NeuronTreeState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }

    val uiState = _uiState.asStateFlow()

    init {
        if (neuronId != null) {
            viewModelScope.launch {
                val neuron = dataRepository.getNeuronById(neuronId).first()
                if (neuron.parentId != null) {
                    val parent = dataRepository.getNeuronById(neuron.parentId).first()
                    state = state.copy(neuron = neuron, parent = parent)
                } else {
                    state = state.copy(neuron = neuron)
                }
            }
            viewModelScope.launch {
                dataRepository.getNeuronsByParentId(neuronId).collect {
                    state = state.copy(children = it)
                }
            }
        } else {
            viewModelScope.launch {
                dataRepository.getRootNeurons().collect {
                    state = state.copy(children = it)
                }
            }
        }
    }

    fun addNeuron() {
        TODO("Not yet implemented")
    }
}

data class NeuronTreeState(
    val neuron: Neuron? = null,
    val parent: Neuron? = null,
    val children: List<Neuron> = emptyList(),
)