package com.example.spottogo.ui.directions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsBus
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.spottogo.data.Restaurant
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

enum class TransportMode { CAR, TRANSIT, WALK }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectionsScreen(restaurant: Restaurant, onBack: () -> Unit) {
    val context = LocalContext.current
    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var selectedMode by remember { mutableStateOf(TransportMode.CAR) }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED
        ) {
            LocationServices.getFusedLocationProviderClient(context)
                .lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        userLocation = LatLng(location.latitude, location.longitude)
                    }
                }
        }
    }

    // Fall back to a position near the restaurant if location is unavailable
    val origin = userLocation ?: LatLng(
        restaurant.latLng.latitude - 0.005,
        restaurant.latLng.longitude - 0.003
    )
    val destination = restaurant.latLng
    val midpoint = LatLng(
        (origin.latitude + destination.latitude) / 2.0,
        (origin.longitude + destination.longitude) / 2.0
    )

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(midpoint, 13f)
    }

    LaunchedEffect(origin) {
        cameraPositionState.animate(CameraUpdateFactory.newLatLngZoom(midpoint, 13f))
    }

    // Estimate travel times from stored distance
    val distanceKm = restaurant.distanceMeters / 1000f
    val carMin = maxOf(1, restaurant.distanceMeters / 400)
    val transitMin = maxOf(1, restaurant.distanceMeters / 200)
    val walkMin = maxOf(1, restaurant.distanceMeters / 80)
    val displayMin = when (selectedMode) {
        TransportMode.CAR -> carMin
        TransportMode.TRANSIT -> transitMin
        TransportMode.WALK -> walkMin
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Directions") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Origin / Destination card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                        )
                        Text("My Location", style = MaterialTheme.typography.bodyMedium)
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = restaurant.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Transport mode selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TransportMode.entries.forEach { mode ->
                    FilterChip(
                        selected = selectedMode == mode,
                        onClick = { selectedMode = mode },
                        label = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = when (mode) {
                                        TransportMode.CAR -> Icons.Default.DirectionsCar
                                        TransportMode.TRANSIT -> Icons.Default.DirectionsBus
                                        TransportMode.WALK -> Icons.Default.DirectionsWalk
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = when (mode) {
                                        TransportMode.CAR -> "${carMin}m"
                                        TransportMode.TRANSIT -> "${transitMin}m"
                                        TransportMode.WALK -> "${walkMin}m"
                                    },
                                    fontSize = 13.sp
                                )
                            }
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // ETA row
            Row(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$displayMin min  ·  ${"%.1f".format(distanceKm)} km",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Text("·", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = "Via Main Street",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Map showing origin and destination markers
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                cameraPositionState = cameraPositionState,
                properties = MapProperties(isMyLocationEnabled = userLocation != null),
                uiSettings = MapUiSettings(
                    myLocationButtonEnabled = false,
                    zoomControlsEnabled = false
                )
            ) {
                Marker(
                    state = MarkerState(position = origin),
                    title = "My Location"
                )
                Marker(
                    state = MarkerState(position = destination),
                    title = restaurant.name,
                    snippet = "${restaurant.cuisine} · ★${restaurant.rating}"
                )
            }

            // START button — launches Google Maps turn-by-turn navigation
            Button(
                onClick = {
                    val dirFlag = when (selectedMode) {
                        TransportMode.CAR -> "d"
                        TransportMode.TRANSIT -> "r"
                        TransportMode.WALK -> "w"
                    }
                    val uri = Uri.parse(
                        "https://maps.google.com/maps" +
                        "?saddr=${origin.latitude},${origin.longitude}" +
                        "&daddr=${destination.latitude},${destination.longitude}" +
                        "&dirflg=$dirFlag"
                    )
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Navigation,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("START", fontSize = 16.sp, letterSpacing = 1.sp)
            }
        }
    }
}
