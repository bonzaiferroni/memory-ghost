package com.bollwerks.memoryghost.ui.neuron

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bollwerks.memoryghost.AppRoutes
import com.bollwerks.memoryghost.data.DataRepository
import com.bollwerks.memoryghost.data.export.NeuronNode
import com.bollwerks.memoryghost.data.export.importNeurons
import com.bollwerks.memoryghost.data.export.toNeuronNodes
import com.bollwerks.memoryghost.model.Neuron
import com.bollwerks.memoryghost.utils.jsonToObject
import com.bollwerks.memoryghost.utils.loadJsonFromFile
import com.bollwerks.memoryghost.utils.objectToFile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class NeuronTreeModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {

    private val neuronId: Int = AppRoutes.Neuron.getId(savedStateHandle = savedStateHandle) ?: 0

    private val _uiState = MutableStateFlow(NeuronTreeState())
    private var state
        get() = _uiState.value
        set(value) {
            _uiState.value = value
        }

    val uiState = _uiState.asStateFlow()

    init {
        if (neuronId != 0) {
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
        state = state.copy(
            showEditNeuronDialog = true,
            editNeuronId = 0,
            editNeuronName = "",
            editNeuronValue = "",
        )
    }

    fun editNeuron() {
        state = state.copy(
            showEditNeuronDialog = true,
            editNeuronId = state.neuron?.id ?: 0,
            editNeuronName = state.neuron?.name ?: "",
            editNeuronValue = state.neuron?.answer ?: "",
        )
    }

    fun editNeuronName(name: String) {
        state = state.copy(editNeuronName = name)
    }

    fun editNeuronValue(value: String) {
        state = state.copy(editNeuronValue = value)
    }

    fun cancelEditNeuron() {
        state = state.copy(showEditNeuronDialog = false)
    }

    fun acceptEditNeuron() {
        viewModelScope.launch {
            val neuron = Neuron(
                id = state.editNeuronId,
                name = state.editNeuronName,
                answer = state.editNeuronValue.takeIf { it.isNotBlank() },
                parentId = if (state.isNewNeuron) state.neuron?.id else state.neuron?.parentId,
            )
            if (state.isNewNeuron) {
                dataRepository.insertNeuron(neuron)
            } else {
                dataRepository.updateNeuron(neuron)
            }
            state = state.copy(showEditNeuronDialog = false)
        }
    }

    fun exportTree(context: Context) {
        viewModelScope.launch {
            val neurons = dataRepository.getRootNeurons().first()
            val nodes = neurons.toNeuronNodes()
            context.objectToFile(nodes, "neurons.json")
        }
    }

    fun importTree(context: Context) {
        viewModelScope.launch {
            val json = context.loadJsonFromFile("neurons.json")
            val nodes = jsonToObject<List<NeuronNode>>(json)
            dataRepository.importNeurons(nodes)
        }
    }

    fun deleteNeuron(
        onDelete: () -> Unit,
    ) {
        viewModelScope.launch {
            dataRepository.deleteNeuron(neuronId)
            onDelete()
        }
    }

    fun showMultiAddDialog() {
        state = state.copy(
            showMultiAddDialog = true,
            multiAddText = "",
        )
    }

    fun onMultiAddChange(text: String) {
        state = state.copy(multiAddText = text)
    }

    fun acceptMultiAdd() {
        viewModelScope.launch {
            val neurons = state.multiAddText.split("\n").map { line ->
                val parts = line.split(":")
                Neuron(
                    name = parts[0],
                    answer = parts.getOrNull(1)?.trim(),
                    parentId = state.neuron?.id,
                )
            }
            neurons.forEach {
                dataRepository.insertNeuron(it)
            }
            state = state.copy(showMultiAddDialog = false)
        }
    }

    fun cancelMultiAdd() {
        state = state.copy(showMultiAddDialog = false)
    }
}

data class NeuronTreeState(
    val neuron: Neuron? = null,
    val parent: Neuron? = null,
    val children: List<Neuron> = emptyList(),
    val showEditNeuronDialog: Boolean = false,
    val editNeuronId: Int = 0,
    val editNeuronName: String = "",
    val editNeuronValue: String = "",
    val showMultiAddDialog: Boolean = false,
    val multiAddText: String = "",
) {
    val isNewNeuron: Boolean
        get() = editNeuronId == 0
    val isValidEditNeuron: Boolean
        get() = editNeuronName.isNotBlank()
}