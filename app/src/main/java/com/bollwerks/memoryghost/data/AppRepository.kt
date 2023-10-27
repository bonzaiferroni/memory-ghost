package com.bollwerks.memoryghost.data

import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun getAllNeurons(): Flow<List<Neuron>>
    fun getNeuronById(id: Int): Flow<Neuron>
    fun searchNeuronByName(name: String): Flow<List<Neuron>>
    suspend fun insert(neuron: Neuron): Int
    suspend fun update(neuron: Neuron)
    suspend fun delete(neuron: Neuron)
}