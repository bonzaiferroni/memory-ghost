package com.bollwerks.memoryghost.data

import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SampleRepository : AppRepository {
    override fun getAllNeurons(): Flow<List<Neuron>> {
        return flowOf(SampleData.neurons)
    }

    override fun getNeuronById(id: Int): Flow<Neuron> {
        return flowOf(SampleData.neurons.first { it.id == id })
    }

    override fun searchNeuronByName(name: String): Flow<List<Neuron>> {
        return flowOf(SampleData.neurons.filter { it.name.contains(name) })
    }

    override suspend fun insert(neuron: Neuron): Int {
        return 0
    }

    override suspend fun update(neuron: Neuron) {
        // no-op
    }

    override suspend fun delete(neuron: Neuron) {
        // no-op
    }
}