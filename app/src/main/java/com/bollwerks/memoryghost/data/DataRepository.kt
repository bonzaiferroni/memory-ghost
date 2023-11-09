package com.bollwerks.memoryghost.data

import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.Flow

interface DataRepository {
    fun getAllNeurons(): Flow<List<Neuron>>
    fun getNeuronById(id: Int): Flow<Neuron>
    fun getNeuronsByParentId(parentId: Int): Flow<List<Neuron>>
    fun getRootNeurons(): Flow<List<Neuron>>
    fun searchNeuronByName(name: String): Flow<List<Neuron>>
    suspend fun insertNeuron(neuron: Neuron): Int
    suspend fun updateNeuron(neuron: Neuron)
    suspend fun deleteNeuron(id: Int)
}