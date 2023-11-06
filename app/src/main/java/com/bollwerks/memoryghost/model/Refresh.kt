package com.bollwerks.memoryghost.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "refresh")
data class Refresh (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val time: Instant = Instant.now(),
    val correct: Boolean = false,
)