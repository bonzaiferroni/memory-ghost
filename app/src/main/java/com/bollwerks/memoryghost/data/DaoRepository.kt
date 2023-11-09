package com.bollwerks.memoryghost.data

import com.bollwerks.memoryghost.data.dao.NeuronDao
import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.Flow

class DaoRepository(
    private val neuronDao: NeuronDao
) : DataRepository {
    override fun getAllNeurons(): Flow<List<Neuron>> {
        return neuronDao.getAll()
    }

    override fun getNeuronById(id: Int): Flow<Neuron> {
        return neuronDao.getById(id)
    }

    override fun getNeuronsByParentId(parentId: Int): Flow<List<Neuron>> {
        return neuronDao.getByParentId(parentId)
    }

    override fun getRootNeurons(): Flow<List<Neuron>> {
        return neuronDao.getRoots()
    }

    override fun searchNeuronByName(name: String): Flow<List<Neuron>> {
        return neuronDao.searchByName(name)
    }

    override suspend fun insertNeuron(neuron: Neuron): Int {
        return neuronDao.insert(neuron).toInt()
    }

    override suspend fun updateNeuron(neuron: Neuron) {
        neuronDao.update(neuron)
    }

    override suspend fun deleteNeuron(id: Int) {
        neuronDao.delete(Neuron(id = id))
    }
}