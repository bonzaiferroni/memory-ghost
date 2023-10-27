package com.bollwerks.memoryghost.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bollwerks.memoryghost.model.Neuron
import kotlinx.coroutines.flow.Flow

@Dao
interface NeuronDao {
    @Query("SELECT * FROM neuron")
    fun getAll(): Flow<List<Neuron>>

    @Query("SELECT * FROM neuron WHERE id = :id")
    fun getById(id: Int): Flow<Neuron>

    @Query("SELECT * FROM neuron WHERE name LIKE :name")
    fun searchByName(name: String): Flow<List<Neuron>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(neuron: Neuron): Long

    @Update
    suspend fun update(neuron: Neuron)

    @Delete
    suspend fun delete(neuron: Neuron)
}