package com.example.androidroom.webService

import com.example.androidroom.common.Constants
import com.example.androidroom.database.MarkerEntity
import com.example.androidroom.viewModel.MarkerViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance(private val viewModel: MarkerViewModel) {

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://apidev.meep.me/tripplan/api/v1/routers/lisboa/resources/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun searchUsers(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(APIService::class.java).getDataFromMarkers(Constants.queryMarker)
            call.body()?.forEach {
                val marker = MarkerEntity(it.id, it.name, it.x, it.y, it.station, it.availableResources, it.spacesAvailable, it.allowDropoff, it.bikesAvailable, it.licencePlate, it.range, it.batteryLevel, it.helmets, it.model, it.resourceImageId, "", it.realTimeData, it.resourceType, it.companyZoneId)
                viewModel.insert(marker)
            }
        }
    }
}