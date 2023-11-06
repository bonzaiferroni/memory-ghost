package com.bollwerks.memoryghost.data.export

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
                value = it.value,
                children = this.toNeuronNodes(it.id)
            )
        }
}