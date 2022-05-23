package com.example.androidroom.view

import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.androidroom.utils.CustomInfoWindowForGoogleMap
import com.example.androidroom.viewModel.MarkerViewModel
import com.example.meeptest.R
import com.example.androidroom.webService.RetrofitInstance
import com.example.androidroom.database.MarkerEntity
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.BitmapDescriptorFactory
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var northeast = 0.0
    private var southwest = 0.0

    private val viewModel: MarkerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val retro = RetrofitInstance(viewModel)
        retro.searchUsers()

        viewModel.allMarker.observe(this){ markerList ->
            if(markerList.isNotEmpty()){
                setContent {
                    Surface(color = MaterialTheme.colors.background) {
                        GoogleMapMultipleMarker(markerList)
                    }
                }
            }
        }
    }

    @Composable
    fun GoogleMapMultipleMarker(list : List<MarkerEntity>) {
        val locationA = Location("point A")
        val locationB = Location("point B")

        val mapview = rememberMapViewWithLifeCycle()
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                AndroidView(
                    { mapview }
                ) { mapView ->
                    CoroutineScope(Dispatchers.Main).launch {
                        mapView.getMapAsync { googleM ->
                            googleM.mapType = 1
                            googleM.uiSettings.isZoomControlsEnabled = true

                            val mark1 = list[0].y?.let { list[0].x?.let { it1 -> LatLng(it, it1) } }

                            googleM?.setOnCameraMoveStartedListener { reasonCode ->

                                if (reasonCode == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                                    googleM.clear()
                                    val curScreen: LatLngBounds = googleM.projection.visibleRegion.latLngBounds
                                    northeast = curScreen.northeast.latitude
                                    southwest = curScreen.northeast.longitude

                                    list.forEach {

                                        locationA.latitude = it.x!!
                                        locationA.longitude = it.y!!
                                        locationB.latitude = southwest
                                        locationB.longitude = northeast

                                        val distance = locationA.distanceTo(locationB)

                                        if(distance < 2000){
                                            var markerOptions: MarkerOptions? = mark1?.let { it1 -> MarkerOptions()
                                                .title(it.name).position(it1)
                                            }

                                            val mark =  LatLng(it.y, it.x)
                                            var description = ""

                                            googleM.setInfoWindowAdapter(
                                                CustomInfoWindowForGoogleMap(this@MainActivity)
                                            )

                                            if(it.companyZoneId == 473){
                                                description = "Id: ${it.id}\nX: ${it.x}\nY: ${it.y}\nLicencePlate: ${it.licencePlate}\nRange: ${it.range}\nBatteryLevel: ${it.batteryLevel}\nHelmets: ${it.helmets}\nModel: ${it.model}\nResourceImages: ${it.resourceImageId}\n${it.licencePlate}\nresourcesImagesUrls: ${it.resourceImageId}\n" +
                                                        "realTimeData: ${it.realTimeData}\n" +
                                                        "ResourceType: ${it.resourceType}\nCompanyZoneId: ${it.companyZoneId}"

                                                markerOptions = MarkerOptions()
                                                    .title(it.name)
                                                    .position(mark)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.motorcycle))
                                                    .snippet(description)

                                            }else if(it.companyZoneId == 412){
                                                description = "Id: ${it.id}\nX: ${it.x}\nY: ${it.y}\nRealTimeData: ${it.realTimeData}\nStation: ${it.station}\nAvailableResources: ${it.availableResources}\nSpacesAvailable: ${it.spacesAvailable}\nAllowDropoff: ${it.allowDropoff}\nCompanyZoneId: ${it.companyZoneId}\nBikesAvailable: ${it.bikesAvailable}"
                                                markerOptions = MarkerOptions()
                                                    .title(it.name)
                                                    .position(mark)
                                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.bicycle))
                                                    .snippet(description)
                                            }
                                            googleM.addMarker(markerOptions)
                                        }
                                    }
                                }
                            }
                            googleM.moveCamera(CameraUpdateFactory.newLatLngZoom(mark1, 20f))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun rememberMapLifeCycleObserver(
        mapView: MapView
    ): LifecycleEventObserver = remember(mapView) {
        LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }

    @Composable
    fun rememberMapViewWithLifeCycle(): MapView {
        val context = LocalContext.current
        val mapView = remember {
            MapView(context).apply {
                id = com.google.maps.android.ktx.R.id.map_frame
            }
        }

        val lifecycleObserver = rememberMapLifeCycleObserver(mapView = mapView)
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(lifecycle) {
            lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleObserver)
            }
        }

        return mapView
    }
}