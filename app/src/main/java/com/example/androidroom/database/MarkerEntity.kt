package com.example.androidroom.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkerEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "x") val x: Double?,
    @ColumnInfo(name = "y") val y: Double?,
    @ColumnInfo(name = "station") val station: Boolean?,
    @ColumnInfo(name = "availableResources") val availableResources: Int?,
    @ColumnInfo(name = "spacesAvailable") val spacesAvailable: Int?,
    @ColumnInfo(name = "allowDropoff") val allowDropoff: Boolean?,
    @ColumnInfo(name = "bikesAvailable") val bikesAvailable: Int?,
    @ColumnInfo(name = "licencePlate") val licencePlate: String?,
    @ColumnInfo(name = "range") val range: Int?,
    @ColumnInfo(name = "batteryLevel") val batteryLevel: Int?,
    @ColumnInfo(name = "helmets") val helmets: Int?,
    @ColumnInfo(name = "model") val model: String?,
    @ColumnInfo(name = "resourceImageId") val resourceImageId: String?,
    @ColumnInfo(name = "resourcesImagesUrls") val resourcesImagesUrls: String?,
    @ColumnInfo(name = "realTimeData") val realTimeData: Boolean?,
    @ColumnInfo(name = "resourceType") val resourceType: String?,
    @ColumnInfo(name = "companyZoneId") val companyZoneId: Int?,
)
