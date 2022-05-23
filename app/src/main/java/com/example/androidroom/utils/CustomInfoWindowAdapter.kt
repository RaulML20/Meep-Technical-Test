package com.example.androidroom.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.example.meeptest.R
import com.google.android.libraries.maps.GoogleMap

class CustomInfoWindowForGoogleMap(context: Context) : GoogleMap.InfoWindowAdapter {

    var mContext = context
    var mWindow = (context as Activity).layoutInflater.inflate(R.layout.info_window, null)

    private fun rendowWindowText(marker: com.google.android.libraries.maps.model.Marker, view: View){

        val tvTitle = view.findViewById<TextView>(R.id.title)
        val tvSnippet = view.findViewById<TextView>(R.id.batteryLevel)

        tvTitle.text = marker.title
        tvSnippet.text = marker.snippet

    }

    override fun getInfoContents(marker: com.google.android.libraries.maps.model.Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }

    override fun getInfoWindow(marker: com.google.android.libraries.maps.model.Marker): View? {
        rendowWindowText(marker, mWindow)
        return mWindow
    }
}