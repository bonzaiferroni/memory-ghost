package com.bollwerks.memoryghost.ui.neuron

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bollwerks.memoryghost.data.DataRepository

class NeuronListModel(
    private val savedStateHandle: SavedStateHandle,
    private val dataRepository: DataRepository,
) : ViewModel() {
    fun addNeuron() {
        TODO("Not yet implemented")
    }
}