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
    val distanceMeters: Int
)

object RestaurantRepository {

    fun getSeedRestaurants(userLat: Double, userLng: Double): List<Restaurant> {
        return listOf(
            Restaurant(
                placeId = "place_001",
                name = "The Spice Garden",
                rating = 4.5f,
                cuisine = "Indian",
                address = "12 Harbour Street",
                latLng = LatLng(userLat + 0.004, userLng + 0.002),
                videoUrl = "https://www.youtube.com/watch?v=Oo6HXisGLoM",
                distanceMeters = 450
            ),
            Restaurant(
                placeId = "place_002",
                name = "Noodle House",
                rating = 4.2f,
                cuisine = "Chinese",
                address = "88 West Avenue",
                latLng = LatLng(userLat - 0.003, userLng + 0.005),
                videoUrl = "https://www.youtube.com/watch?v=ZJthWmU0cKk",
                distanceMeters = 620
            ),
            Restaurant(
                placeId = "place_003",
                name = "Bella Italia",
                rating = 4.7f,
                cuisine = "Italian",
                address = "5 Crown Road",
                latLng = LatLng(userLat + 0.002, userLng - 0.004),
                videoUrl = "https://www.youtube.com/watch?v=lsNpOFcjLQg",
                distanceMeters = 310
            ),
            Restaurant(
                placeId = "place_004",
                name = "Burger Republic",
                rating = 4.0f,
                cuisine = "American",
                address = "27 Market Lane",
                latLng = LatLng(userLat - 0.005, userLng - 0.003),
                videoUrl = "https://www.youtube.com/watch?v=RUV7IbcDzrk",
                distanceMeters = 780
            ),
            Restaurant(
                placeId = "place_005",
                name = "Sushi World",
                rating = 4.8f,
                cuisine = "Japanese",
                address = "31 Ocean Boulevard",
                latLng = LatLng(userLat + 0.007, userLng + 0.001),
                videoUrl = "https://www.youtube.com/watch?v=N1C6HBH4VrA",
                distanceMeters = 950
            )
        )
    }
}
