package com.example.spottogo.ui.map

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.spottogo.data.GeminiSearchService
import com.example.spottogo.data.PlacesRepository
import com.example.spottogo.data.Restaurant
import com.example.spottogo.data.RestaurantSearch
import com.example.spottogo.data.SearchIntent
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapScreen(
    onRestaurantClick: (Restaurant) -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToContact: () -> Unit,
    onNavigateToPrivacy: () -> Unit,
    onLogout: () -> Unit
) {
    val context = LocalContext.current
    val locationPermission = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // nearbyRestaurants is the live Places Nearby Search result for the user's location;
    // searchResults is a live Places Text Search result scoped to the current query, or
    // null when there's no active query (in which case nearbyRestaurants is shown instead).
    var nearbyRestaurants by remember { mutableStateOf<List<Restaurant>>(emptyList()) }
    var searchResults by remember { mutableStateOf<List<Restaurant>?>(null) }
    var isLoadingNearby by remember { mutableStateOf(true) }
    var isSearching by remember { mutableStateOf(false) }
    var loadError by remember { mutableStateOf<String?>(null) }
    var userLatLng by remember { mutableStateOf<LatLng?>(null) }

    var searchQuery by remember { mutableStateOf("") }
    var searchIntent by remember { mutableStateOf<SearchIntent?>(null) }
    var reloadTrigger by remember { mutableStateOf(0) }

    val defaultLocation = LatLng(51.5074, -0.1278)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 14f)
    }

    // Press-back-again-to-exit: the Map screen is the first screen reached after login, so a
    // single back press here would otherwise exit the app with no confirmation.
    var backPressedOnce by remember { mutableStateOf(false) }
    BackHandler(enabled = true) {
        if (backPressedOnce) {
            (context as? Activity)?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
    }
    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            delay(2000)
            backPressedOnce = false
        }
    }

    LaunchedEffect(Unit) {
        if (!locationPermission.status.isGranted) {
            locationPermission.launchPermissionRequest()
        }
    }

    // Resolves the user's location, then loads live nearby restaurants from the Places API.
    // Keyed on reloadTrigger too, so the Retry button on an error state can re-run this.
    LaunchedEffect(locationPermission.status.isGranted, reloadTrigger) {
        isLoadingNearby = true
        loadError = null

        val resolvedLatLng = if (locationPermission.status.isGranted &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            val fusedClient = LocationServices.getFusedLocationProviderClient(context)
            val location = runCatching { fusedClient.lastLocation.await() }.getOrNull()
            if (location != null) LatLng(location.latitude, location.longitude) else defaultLocation
        } else {
            defaultLocation
        }

        userLatLng = resolvedLatLng
        cameraPositionState.move(CameraUpdateFactory.newLatLngZoom(resolvedLatLng, 14f))

        PlacesRepository.fetchNearby(resolvedLatLng.latitude, resolvedLatLng.longitude)
            .onSuccess { nearbyRestaurants = it }
            .onFailure { error ->
                loadError = error.message ?: "Couldn't load nearby restaurants"
            }
        isLoadingNearby = false
    }

    // Debounce the query, then run two things in parallel: a live Places Text Search scoped
    // to the query (replacing the nearby list while a search is active), and a Gemini call
    // that turns the query into structured filters (cuisine, price range, vibe) applied on
    // top of whichever list is showing. Resetting both to their "no query" state up front
    // means the plain substring filter is used as an instant fallback while both are in
    // flight or if either fails.
    LaunchedEffect(searchQuery, userLatLng) {
        val location = userLatLng
        if (searchQuery.isBlank() || location == null) {
            searchResults = null
            searchIntent = null
            isSearching = false
            return@LaunchedEffect
        }
        searchIntent = null
        isSearching = true
        delay(600)

        val textSearch = PlacesRepository.searchByText(location.latitude, location.longitude, searchQuery)
        searchResults = textSearch.getOrNull()

        val intentResult = GeminiSearchService.interpret(searchQuery)
        searchIntent = intentResult.getOrNull()
        isSearching = false
    }

    val filteredRestaurants = RestaurantSearch.filter(
        restaurants = searchResults ?: nearbyRestaurants,
        query = searchQuery,
        intent = searchIntent
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") },
                    selected = false,
                    onClick = onNavigateToHome
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Map, contentDescription = "Map") },
                    label = { Text("Map") },
                    selected = true,
                    onClick = {}
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.SupportAgent, contentDescription = "Contact") },
                    label = { Text("Contact") },
                    selected = false,
                    onClick = onNavigateToContact
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Policy, contentDescription = "Privacy") },
                    label = { Text("Privacy") },
                    selected = false,
                    onClick = onNavigateToPrivacy
                )
                NavigationBarItem(
                    icon = { Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout") },
                    label = { Text("Logout") },
                    selected = false,
                    onClick = onLogout
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = locationPermission.status.isGranted),
                uiSettings = MapUiSettings(myLocationButtonEnabled = true)
            ) {
                filteredRestaurants.forEach { restaurant ->
                    Marker(
                        state = MarkerState(position = restaurant.latLng),
                        title = restaurant.name,
                        snippet = "${restaurant.cuisine} • ★${restaurant.rating}",
                        onClick = {
                            onRestaurantClick(restaurant)
                            true
                        }
                    )
                }
            }

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Try \"quiet cheap place for a date\"...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    if (isSearching) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp))
                    }
                },
                shape = RoundedCornerShape(28.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 48.dp)
                    .align(Alignment.TopCenter),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                    focusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )

            if (isLoadingNearby) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 96.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (loadError != null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Couldn't load nearby restaurants.\n$loadError",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(
                            onClick = { reloadTrigger++ },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}
