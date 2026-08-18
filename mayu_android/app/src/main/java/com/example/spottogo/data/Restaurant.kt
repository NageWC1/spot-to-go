package com.example.spottogo.data

import com.google.android.gms.maps.model.LatLng

data class Restaurant(
    val placeId: String,
    val name: String,
    val rating: Float,
    val cuisine: String,
    val address: String,
    val latLng: LatLng,
    val videoUrl: String,
    val videoAuthor: String,
    val distanceMeters: Int,
    val priceRange: String,
    val vibeTags: List<String>
)
