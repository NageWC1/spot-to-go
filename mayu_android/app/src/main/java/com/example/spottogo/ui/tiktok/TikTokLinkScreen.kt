package com.example.spottogo.ui.tiktok

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spottogo.data.Restaurant

private val TikTokRed = Color(0xFFFF0050)
private val TikTokDark = Color(0xFF121212)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TikTokLinkScreen(restaurant: Restaurant, onBack: () -> Unit) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // Two-colour TikTok wordmark
                    Text(
                        text = "Tik",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = TikTokDark
                    )
                    Text(
                        text = "Tok",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        color = TikTokRed
                    )
                },
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
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // TikTok-style video preview card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(TikTokDark)
            ) {
                // Centred play icon
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    tint = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier
                        .size(72.dp)
                        .align(Alignment.Center)
                )

                // Right-side action column
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Profile avatar placeholder
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(Color.White.copy(alpha = 0.25f), CircleShape)
                    )
                    TikTokAction(Icons.Default.Favorite, "15.2k")
                    TikTokAction(Icons.Default.ChatBubble, "893")
                    TikTokAction(Icons.Default.Share, "Share")
                }

                // Bottom-left: username, restaurant name, hashtags
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(start = 14.dp, bottom = 20.dp, end = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = "@foodie_explorer",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = restaurant.name,
                        color = Color.White,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "#foodlover #RestaurantReview #${restaurant.cuisine.lowercase()}food",
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 12.sp
                    )
                }
            }

            // Info row below card
            Text(
                text = "${restaurant.name} — shared via TikTok",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                modifier = Modifier.padding(horizontal = 4.dp)
            )

            // OPEN IN TIKTOK APP button
            Button(
                onClick = {
                    val query = Uri.encode(restaurant.name)
                    context.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://www.tiktok.com/search?q=$query")
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = TikTokRed)
            ) {
                Text("OPEN IN TIKTOK APP", fontSize = 15.sp, letterSpacing = 0.5.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
private fun TikTokAction(icon: ImageVector, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
        Text(text = label, color = Color.White, fontSize = 11.sp)
    }
}
