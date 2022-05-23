package com.example.androidroom.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDAO {

    /**
     * Query for select all the markers
     */
    @Query("SELECT * FROM markers ORDER BY id ASC")
    fun getMarkers(): Flow<List<MarkerEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(marker : MarkerEntity)

    @Query("DELETE FROM markers")
    suspend fun deleteAll()
}