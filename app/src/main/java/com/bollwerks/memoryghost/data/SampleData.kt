package com.bollwerks.memoryghost.data

import com.bollwerks.memoryghost.model.Neuron

object SampleData {

    val neurons: List<Neuron> by lazy {
        var id = 0
        listOf(
            Neuron(id = ++id, name = "generate component",
                value = "ng generate component my-component --standalone --skip-tests"),
            Neuron(id = ++id, name = "generate service",
                value = "ng generate service my-service --skip-tests"),
        )
    }
}