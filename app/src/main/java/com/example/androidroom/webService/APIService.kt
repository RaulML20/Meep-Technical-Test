package com.example.androidroom.webService

import com.example.androidroom.model.data.Marker
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    /**
     * Coroutine for get data from the endpoint
     */
    @GET
    suspend fun getDataFromMarkers(@Url url: String): Response<List<Marker>>
}