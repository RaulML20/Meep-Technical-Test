package com.example.androidroom.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MarkerEntity::class], version = 2, exportSchema = false)
abstract class MarkerDatabase : RoomDatabase() {
    abstract fun markerDao(): MarkerDAO
}