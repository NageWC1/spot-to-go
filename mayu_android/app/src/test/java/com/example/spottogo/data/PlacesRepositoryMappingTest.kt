package com.example.spottogo.data

import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Exercises PlacesRepository.toRestaurant against hand-built Places API (New) JSON payloads,
 * without making a network call. Covers the mapping decisions that replaced the fixed
 * in-memory seed list: cuisine/price/vibe derivation and the curated-video fallback.
 */
class PlacesRepositoryMappingTest {

    // London coordinates, used as a fixed user location across these tests.
    private val userLat = 51.5074
    private val userLng = -0.1278

    private fun placeJson(
        id: String = "place_id_123",
        name: String = "Test Restaurant",
        rating: Double? = 4.5,
        address: String = "1 Test Street",
        lat: Double = 51.51,
        lng: Double = -0.12,
        primaryType: String = "restaurant",
        priceLevel: String? = null
    ) = JSONObject().apply {
        put("id", id)
        put("displayName", JSONObject().apply { put("text", name) })
        rating?.let { put("rating", it) }
        put("formattedAddress", address)
        put("location", JSONObject().apply {
            put("latitude", lat)
            put("longitude", lng)
        })
        put("primaryType", primaryType)
        priceLevel?.let { put("priceLevel", it) }
    }

    @Test
    fun `known cuisine type gets its curated demo video`() {
        val restaurant = PlacesRepository.toRestaurant(
            placeJson(primaryType = "italian_restaurant"), userLat, userLng
        )
        assertEquals("Italian", restaurant.cuisine)
        assertTrue(restaurant.videoUrl.contains("youtube.com/watch"))
    }

    @Test
    fun `unrecognised cuisine type falls back to a youtube search link`() {
        val restaurant = PlacesRepository.toRestaurant(
            placeJson(primaryType = "thai_restaurant"), userLat, userLng
        )
        assertEquals("Thai", restaurant.cuisine)
        assertTrue(restaurant.videoUrl.startsWith("https://www.youtube.com/results?search_query="))
    }

    @Test
    fun `price level maps to the app's three price bands`() {
        val budget = PlacesRepository.toRestaurant(placeJson(priceLevel = "PRICE_LEVEL_INEXPENSIVE"), userLat, userLng)
        val midRange = PlacesRepository.toRestaurant(placeJson(priceLevel = null), userLat, userLng)
        val premium = PlacesRepository.toRestaurant(placeJson(priceLevel = "PRICE_LEVEL_VERY_EXPENSIVE"), userLat, userLng)

        assertEquals("budget", budget.priceRange)
        assertEquals("mid-range", midRange.priceRange)
        assertEquals("premium", premium.priceRange)
    }

    @Test
    fun `distance is computed from the user's coordinates, not left at zero`() {
        val restaurant = PlacesRepository.toRestaurant(
            placeJson(lat = userLat + 0.01, lng = userLng), userLat, userLng
        )
        // ~0.01 degrees of latitude is a little over 1km.
        assertTrue(restaurant.distanceMeters in 1000..1300)
    }

    @Test
    fun `missing display name falls back to a placeholder instead of crashing`() {
        val json = placeJson().apply { getJSONObject("displayName").put("text", "") }
        val restaurant = PlacesRepository.toRestaurant(json, userLat, userLng)
        assertEquals("Unnamed Restaurant", restaurant.name)
    }
}
