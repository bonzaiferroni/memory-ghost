package com.bollwerks.memoryghost.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bollwerks.memoryghost.data.dao.NeuronDao
import com.bollwerks.memoryghost.model.Neuron

@Database(entities = [Neuron::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun neuronDao(): NeuronDao

    companion object {
        fun getDatabase(context: Context) : AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                .build()
        }
    }
}