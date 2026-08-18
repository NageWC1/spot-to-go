package com.example.spottogo.data

import com.example.spottogo.BuildConfig
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * A restaurant's cuisine paired with a demo video. Reused across every live result of that
 * cuisine, since Places gives us no video content and per-restaurant curation isn't feasible
 * once results come from a live, unbounded API instead of a fixed seed list.
 */
private data class CuratedVideo(val cuisine: String, val url: String, val author: String)

/**
 * Live restaurant data from the Google Places API (New). Replaces the fixed in-memory seed
 * list: [fetchNearby] powers the initial map load, [searchByText] powers the search bar.
 * Both share the same field mask and JSON-to-[Restaurant] mapping.
 */
object PlacesRepository {

    private const val NEARBY_ENDPOINT = "https://places.googleapis.com/v1/places:searchNearby"
    private const val TEXT_ENDPOINT = "https://places.googleapis.com/v1/places:searchText"
    private const val SEARCH_RADIUS_METERS = 1500.0
    private const val MAX_RESULTS = 20

    private const val FIELD_MASK =
        "places.id,places.displayName,places.rating,places.location," +
            "places.formattedAddress,places.priceLevel,places.primaryType"

    // primaryType values returned by Places API (New) for the five cuisines the project
    // has real demo videos for; anything else falls back to a YouTube search link.
    private val curatedVideosByType = mapOf(
        "indian_restaurant" to CuratedVideo("Indian", "https://www.youtube.com/watch?v=bgzP3yn1kNE", "Gary Eats"),
        "chinese_restaurant" to CuratedVideo("Chinese", "https://www.youtube.com/watch?v=h_qrDLZv-yU", "The Food Ranger"),
        "italian_restaurant" to CuratedVideo("Italian", "https://www.youtube.com/watch?v=5LvDiBa5SuU", "the altem life"),
        "american_restaurant" to CuratedVideo("American", "https://www.youtube.com/watch?v=_Vuq_IGaMNw", "Gary Eats"),
        "japanese_restaurant" to CuratedVideo("Japanese", "https://www.youtube.com/watch?v=3wAQxJeyyXo", "Tasty"),
        "sushi_restaurant" to CuratedVideo("Japanese", "https://www.youtube.com/watch?v=3wAQxJeyyXo", "Tasty")
    )

    /** Nearby Search: restaurants within [SEARCH_RADIUS_METERS] of the user, no text query. */
    suspend fun fetchNearby(userLat: Double, userLng: Double): Result<List<Restaurant>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val body = JSONObject().apply {
                    // includedPrimaryTypes (not includedTypes) is what actually restricts
                    // results to genuine restaurants — includedTypes matched much looser
                    // "food & drink area" places (a department store, a food court) when
                    // tested live against the real API.
                    put("includedPrimaryTypes", JSONArray(listOf("restaurant")))
                    put("maxResultCount", MAX_RESULTS)
                    put("locationRestriction", JSONObject().apply {
                        put("circle", JSONObject().apply {
                            put("center", JSONObject().apply {
                                put("latitude", userLat)
                                put("longitude", userLng)
                            })
                            put("radius", SEARCH_RADIUS_METERS)
                        })
                    })
                }
                val response = post(NEARBY_ENDPOINT, body)
                parsePlaces(response, userLat, userLng)
            }
        }

    /** Text Search: restaurants matching [query], biased to the user's current area. */
    suspend fun searchByText(userLat: Double, userLng: Double, query: String): Result<List<Restaurant>> =
        withContext(Dispatchers.IO) {
            runCatching {
                val body = JSONObject().apply {
                    put("textQuery", "$query restaurant")
                    put("maxResultCount", MAX_RESULTS)
                    put("locationBias", JSONObject().apply {
                        put("circle", JSONObject().apply {
                            put("center", JSONObject().apply {
                                put("latitude", userLat)
                                put("longitude", userLng)
                            })
                            put("radius", SEARCH_RADIUS_METERS)
                        })
                    })
                }
                val response = post(TEXT_ENDPOINT, body)
                parsePlaces(response, userLat, userLng)
            }
        }

    private fun post(endpoint: String, body: JSONObject): String {
        val apiKey = BuildConfig.PLACES_API_KEY
        require(apiKey.isNotBlank()) { "PLACES_API_KEY is not set in local.properties" }

        val connection = URL(endpoint).openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("X-Goog-Api-Key", apiKey)
        connection.setRequestProperty("X-Goog-FieldMask", FIELD_MASK)
        connection.doOutput = true
        connection.outputStream.use { it.write(body.toString().toByteArray()) }

        val responseCode = connection.responseCode
        val responseText = (if (responseCode in 200..299) connection.inputStream else connection.errorStream)
            .bufferedReader()
            .use { it.readText() }

        if (responseCode !in 200..299) {
            throw Exception("Places API error $responseCode: $responseText")
        }
        return responseText
    }

    private fun parsePlaces(responseText: String, userLat: Double, userLng: Double): List<Restaurant> {
        val places = JSONObject(responseText).optJSONArray("places") ?: JSONArray()
        return (0 until places.length()).map { i -> toRestaurant(places.getJSONObject(i), userLat, userLng) }
    }

    internal fun toRestaurant(place: JSONObject, userLat: Double, userLng: Double): Restaurant {
        val placeId = place.getString("id")
        val name = place.optJSONObject("displayName")?.optString("text").orEmpty().ifBlank { "Unnamed Restaurant" }
        val rating = place.optDouble("rating", 0.0).toFloat()
        val address = place.optString("formattedAddress", "Address unavailable")
        val lat = place.getJSONObject("location").getDouble("latitude")
        val lng = place.getJSONObject("location").getDouble("longitude")
        val primaryType = place.optString("primaryType", "restaurant")
        val priceLevel = place.optString("priceLevel", "")
        val priceRange = mapPriceLevel(priceLevel)

        val curated = curatedVideosByType[primaryType]
        val cuisine = curated?.cuisine ?: primaryType.removeSuffix("_restaurant")
            .replace('_', ' ')
            .trim()
            .ifBlank { "Restaurant" }
            .replaceFirstChar { it.uppercase() }

        return Restaurant(
            placeId = placeId,
            name = name,
            rating = rating,
            cuisine = cuisine,
            address = address,
            latLng = LatLng(lat, lng),
            videoUrl = curated?.url ?: youtubeSearchUrl(name),
            videoAuthor = curated?.author ?: "YouTube Search",
            distanceMeters = distanceMeters(userLat, userLng, lat, lng),
            priceRange = priceRange,
            vibeTags = vibeTags(rating, priceRange)
        )
    }

    private fun mapPriceLevel(priceLevel: String): String = when (priceLevel) {
        "PRICE_LEVEL_FREE", "PRICE_LEVEL_INEXPENSIVE" -> "budget"
        "PRICE_LEVEL_EXPENSIVE", "PRICE_LEVEL_VERY_EXPENSIVE" -> "premium"
        else -> "mid-range"
    }

    private fun vibeTags(rating: Float, priceRange: String): List<String> {
        val tags = mutableListOf(if (rating >= 4.5f) "highly-rated" else "casual")
        tags += when (priceRange) {
            "budget" -> "cheap"
            "premium" -> "date-night"
            else -> "family-friendly"
        }
        return tags
    }

    // Haversine formula, kept dependency-free (no android.location.Location) so this is
    // testable in a plain JVM unit test without a device or Robolectric.
    private fun distanceMeters(userLat: Double, userLng: Double, lat: Double, lng: Double): Int {
        val earthRadiusMeters = 6_371_000.0
        val dLat = Math.toRadians(lat - userLat)
        val dLng = Math.toRadians(lng - userLng)
        val a = sin(dLat / 2) * sin(dLat / 2) +
            cos(Math.toRadians(userLat)) * cos(Math.toRadians(lat)) *
            sin(dLng / 2) * sin(dLng / 2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return (earthRadiusMeters * c).toInt()
    }

    private fun youtubeSearchUrl(restaurantName: String): String {
        val encoded = URLEncoder.encode("$restaurantName food review", "UTF-8")
        return "https://www.youtube.com/results?search_query=$encoded"
    }
}
