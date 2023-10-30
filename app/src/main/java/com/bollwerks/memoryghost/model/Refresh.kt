package com.bollwerks.memoryghost.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "refresh",
    foreignKeys = [
        ForeignKey(
            entity = Neuron::class,
            parentColumns = ["id"],
            childColumns = ["neuron_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("name")]
)
data class Refresh (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: Instant = Instant.now(),
    val correct: Boolean = false,
)