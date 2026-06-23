package com.example.spottogo.ui.privacy

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyPolicyScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Privacy Policy") },
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
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Last Updated: April 18, 2025",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = "We value your privacy. This policy explains how we collect, use, and protect your information.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            PolicySection(
                number = "1",
                title = "Information We Collect",
                bullets = listOf(
                    "Location Data — your GPS coordinates to find nearby restaurants",
                    "Device Information — device model and OS version for compatibility",
                    "Usage Data — features you interact with, to improve the app"
                )
            )

            PolicySection(
                number = "2",
                title = "How We Use Information",
                bullets = listOf(
                    "Provide and improve our restaurant discovery services",
                    "Personalise your experience based on location and preferences",
                    "Communicate with you about app updates or support requests"
                )
            )

            PolicySection(
                number = "3",
                title = "Data Protection",
                bullets = listOf(
                    "We implement industry-standard security measures to protect your data",
                    "Your data is never sold to third parties",
                    "You may request deletion of your data at any time by contacting us"
                )
            )

            PolicySection(
                number = "4",
                title = "Third-Party Services",
                bullets = listOf(
                    "Google Maps SDK — used for map display and location services",
                    "YouTube & TikTok — video links open in their respective apps",
                    "These services have their own privacy policies which apply"
                )
            )

            PolicySection(
                number = "5",
                title = "Contact",
                bullets = listOf(
                    "Questions about this policy? Use the Contact Us page in the app",
                    "We will respond within 5 business days"
                )
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun PolicySection(number: String, title: String, bullets: List<String>) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = "$number. $title",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.primary
        )
        bullets.forEach { bullet ->
            BulletRow(text = bullet)
        }
    }
}

@Composable
private fun BulletRow(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Default.Circle,
            contentDescription = null,
            modifier = Modifier
                .size(6.dp)
                .padding(top = 6.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(1f)
        )
    }
}
