package com.bollwerks.memoryghost.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "neuron",
    foreignKeys = [
        ForeignKey(
            entity = Neuron::class,
            parentColumns = ["id"],
            childColumns = ["parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("name")]
)
data class Neuron(
    @PrimaryKey
    val id: Int,
    val name: String,
    val value: String,
    @ColumnInfo(name = "parent_id")
    val parentId: Int,
)