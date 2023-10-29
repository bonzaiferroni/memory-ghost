package com.bollwerks.memoryghost

import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.bollwerks.memoryghost.data.DataRepository
import com.bollwerks.memoryghost.ui.neuron.NeuronListModel

class ViewModelProvider(
    private val dataRepository: DataRepository,
) {
    val Factory = viewModelFactory {
        initializer {
            val savedStateHandle = createSavedStateHandle()
            NeuronListModel(savedStateHandle, dataRepository)
        }
    }
}