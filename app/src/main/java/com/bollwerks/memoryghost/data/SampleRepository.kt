package com.bollwerks.memoryghost.data

import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SampleRepository : DataRepository {
    override fun getAllNeurons(): Flow<List<Neuron>> {
        return flowOf(SampleData.neurons)
    }

    override fun getNeuronById(id: Int): Flow<Neuron> {
        return flowOf(SampleData.neurons.first { it.id == id })
    }

    override fun searchNeuronByName(name: String): Flow<List<Neuron>> {
        return flowOf(SampleData.neurons.filter { it.name.contains(name) })
    }

    override fun getNeuronsByParentId(parentId: Int): Flow<List<Neuron>> {
        return flowOf(SampleData.neurons.filter { it.parentId == parentId })
    }

    override fun getRootNeurons(): Flow<List<Neuron>> {
        return flowOf(SampleData.neurons.filter { it.parentId == null })
    }

    override suspend fun insertNeuron(neuron: Neuron): Int {
        return 0
    }

    override suspend fun updateNeuron(neuron: Neuron) {
        // no-op
    }

    override suspend fun deleteNeuron(id: Int) {
        // no-op
    }
}