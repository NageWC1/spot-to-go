package com.example.spottogo.data

import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class RestaurantSearchTest {

    private fun restaurant(
        name: String,
        cuisine: String,
        priceRange: String = "mid-range",
        vibeTags: List<String> = emptyList()
    ) = Restaurant(
        placeId = name,
        name = name,
        rating = 4.0f,
        cuisine = cuisine,
        address = "1 Test Street",
        latLng = LatLng(0.0, 0.0),
        videoUrl = "https://example.com/video",
        videoAuthor = "Test Author",
        distanceMeters = 100,
        priceRange = priceRange,
        vibeTags = vibeTags
    )

    private val restaurants = listOf(
        restaurant("Bella Italia", "Italian", priceRange = "premium", vibeTags = listOf("romantic", "date-night")),
        restaurant("Noodle House", "Chinese", priceRange = "budget", vibeTags = listOf("casual", "cheap")),
        restaurant("Sushi World", "Japanese", priceRange = "premium", vibeTags = listOf("quiet", "date-night"))
    )

    @Test
    fun `blank query returns the full list unchanged`() {
        val result = RestaurantSearch.filter(restaurants, query = "", intent = null)
        assertEquals(restaurants, result)
    }

    @Test
    fun `plain query with no intent falls back to a name or cuisine substring match`() {
        val result = RestaurantSearch.filter(restaurants, query = "chinese", intent = null)
        assertEquals(listOf("Noodle House"), result.map { it.name })
    }

    @Test
    fun `structured intent filters by cuisine`() {
        val intent = SearchIntent(cuisine = "Italian")
        val result = RestaurantSearch.filter(restaurants, query = "italian food", intent = intent)
        assertEquals(listOf("Bella Italia"), result.map { it.name })
    }

    @Test
    fun `structured intent filters by price range and vibe together`() {
        val intent = SearchIntent(priceRange = "premium", vibe = "quiet")
        val result = RestaurantSearch.filter(restaurants, query = "quiet premium place", intent = intent)
        assertEquals(listOf("Sushi World"), result.map { it.name })
    }

    @Test
    fun `an empty intent falls back to plain substring matching instead of matching everything`() {
        val intent = SearchIntent()
        assertTrue(intent.isEmpty)
        val result = RestaurantSearch.filter(restaurants, query = "sushi", intent = intent)
        assertEquals(listOf("Sushi World"), result.map { it.name })
    }
}
