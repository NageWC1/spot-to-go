package com.example.spottogo.data

/**
 * Client-side refinement applied on top of whichever restaurant list is currently loaded
 * (nearby or a live Places text search). Pulled out of MapScreen as a pure function so the
 * filtering behaviour can be unit tested without a Compose or Android runtime.
 */
object RestaurantSearch {

    fun filter(restaurants: List<Restaurant>, query: String, intent: SearchIntent?): List<Restaurant> {
        if (query.isBlank()) return restaurants

        return if (intent != null && !intent.isEmpty) {
            restaurants.filter { restaurant ->
                (intent.cuisine == null || restaurant.cuisine.contains(intent.cuisine, ignoreCase = true)) &&
                    (intent.priceRange == null || restaurant.priceRange.equals(intent.priceRange, ignoreCase = true)) &&
                    (intent.vibe == null || restaurant.vibeTags.any { tag -> tag.contains(intent.vibe, ignoreCase = true) })
            }
        } else {
            restaurants.filter {
                it.name.contains(query, ignoreCase = true) || it.cuisine.contains(query, ignoreCase = true)
            }
        }
    }
}
