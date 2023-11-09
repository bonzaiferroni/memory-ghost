package com.bollwerks.memoryghost.data.export

import com.bollwerks.memoryghost.data.DataRepository
import com.bollwerks.memoryghost.model.Neuron

data class NeuronNode(
    val name: String,
    val value: String?,
    val children: List<NeuronNode> = emptyList()
)

fun List<Neuron>.toNeuronNodes(parentId: Int? = null): List<NeuronNode> {
    return this.filter { it.parentId == parentId }
        .map {
            NeuronNode(
                name = it.name,
                value = it.answer,
                children = this.toNeuronNodes(it.id)
            )
        }
}

suspend fun DataRepository.importNeurons(nodes: List<NeuronNode>, parentId: Int? = null) {
    nodes.forEach {
        val neuron = Neuron(
            name = it.name,
            answer = it.value,
            parentId = parentId
        )
        val id = this.insertNeuron(neuron)
        this.importNeurons(nodes = it.children, parentId = id)
    }
}