package com.example.spottogo.ui.detail

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spottogo.data.Restaurant
import com.google.android.gms.maps.model.LatLng
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Functional test for the Restaurant Detail screen: verifies the restaurant's data renders
 * and that its two primary actions ("Watch Video", "Get Directions") fire their callbacks.
 */
@RunWith(AndroidJUnit4::class)
class RestaurantDetailScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val restaurant = Restaurant(
        placeId = "test_place_id",
        name = "Bella Italia",
        rating = 4.7f,
        cuisine = "Italian",
        address = "5 Crown Road",
        latLng = LatLng(51.51, -0.12),
        videoUrl = "https://www.youtube.com/watch?v=5LvDiBa5SuU",
        videoAuthor = "the altem life",
        distanceMeters = 310,
        priceRange = "premium",
        vibeTags = listOf("romantic", "quiet", "date-night")
    )

    @Test
    fun displaysRestaurantDetails() {
        composeRule.setContent {
            RestaurantDetailScreen(restaurant = restaurant, onBack = {})
        }

        composeRule.onNodeWithText("Bella Italia").assertExists()
        composeRule.onNodeWithText("Italian").assertExists()
        composeRule.onNodeWithText("5 Crown Road").assertExists()
        composeRule.onNodeWithText("310m away").assertExists()
    }

    @Test
    fun watchVideoButtonFiresCallback() {
        var watchVideoClicked = false
        composeRule.setContent {
            RestaurantDetailScreen(
                restaurant = restaurant,
                onBack = {},
                onWatchVideo = { watchVideoClicked = true }
            )
        }

        composeRule.onNodeWithText("Watch Video Preview").performClick()
        assert(watchVideoClicked)
    }

    @Test
    fun getDirectionsButtonFiresCallback() {
        var directionsClicked = false
        composeRule.setContent {
            RestaurantDetailScreen(
                restaurant = restaurant,
                onBack = {},
                onGetDirections = { directionsClicked = true }
            )
        }

        composeRule.onNodeWithText("Get Directions").performClick()
        assert(directionsClicked)
    }

    @Test
    fun backButtonFiresCallback() {
        var backClicked = false
        composeRule.setContent {
            RestaurantDetailScreen(restaurant = restaurant, onBack = { backClicked = true })
        }

        composeRule.onNodeWithContentDescription("Back").performClick()
        assert(backClicked)
    }
}
