package com.example.androidroom.model.data

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "markers")
data class Marker(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("x") val x: Double,
    @SerializedName("y") val y: Double,
    @SerializedName("station") val station: Boolean?,
    @SerializedName("availableResources") val availableResources: Int,
    @SerializedName("spacesAvailable") val spacesAvailable: Int,
    @SerializedName("allowDropoff") val allowDropoff: Boolean,
    @SerializedName("bikesAvailable") val bikesAvailable: Int,
    @SerializedName("licencePlate") val licencePlate: String,
    @SerializedName("range") val range: Int,
    @SerializedName("batteryLevel") val batteryLevel: Int,
    @SerializedName("helmets") val helmets: Int,
    @SerializedName("model") val model: String,
    @SerializedName("resourceImageId") val resourceImageId: String,
    @SerializedName("resourcesImagesUrls") val resourcesImagesUrls: List<String>,
    @SerializedName("realTimeData") val realTimeData: Boolean,
    @SerializedName("resourceType") val resourceType: String,
    @SerializedName("companyZoneId") val companyZoneId: Int,
)
