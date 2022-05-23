package com.example.androidroom.repository

import androidx.annotation.WorkerThread
import com.example.androidroom.database.MarkerDAO
import com.example.androidroom.database.MarkerEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MarkerRepository @Inject constructor(private val markerDAO: MarkerDAO) {

    val allMarkers: Flow<List<MarkerEntity>> = markerDAO.getMarkers()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(marker: MarkerEntity) {
        markerDAO.insert(marker)
    }
}