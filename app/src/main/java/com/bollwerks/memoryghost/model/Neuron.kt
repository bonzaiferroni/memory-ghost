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
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val answer: String? = null,
    @ColumnInfo(name = "parent_id")
    val parentId: Int? = null,
    val priority: Int = 0,
    val interval: Int = 0,
    val overprep: Int = 0,
    val strength: Int = 0,
    val refreshed: Long? = null,
)