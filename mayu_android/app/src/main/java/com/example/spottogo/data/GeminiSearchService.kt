package com.example.spottogo.data

import com.example.spottogo.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

data class SearchIntent(
    val cuisine: String? = null,
    val priceRange: String? = null,
    val vibe: String? = null
) {
    val isEmpty: Boolean get() = cuisine == null && priceRange == null && vibe == null
}

/**
 * Turns a free-text search query (e.g. "quiet cheap place for a date") into structured
 * filter attributes using the Gemini API. This is the "agentic" part of the search bar:
 * the model interprets user intent rather than the app matching exact keywords.
 */
object GeminiSearchService {

    private const val MODEL = "gemini-3.6-flash"
    private const val ENDPOINT =
        "https://generativelanguage.googleapis.com/v1beta/models/$MODEL:generateContent"

    suspend fun interpret(query: String): Result<SearchIntent> = withContext(Dispatchers.IO) {
        try {
            val apiKey = BuildConfig.GEMINI_API_KEY
            require(apiKey.isNotBlank()) { "GEMINI_API_KEY is not set in local.properties" }

            val prompt = """
                Extract restaurant search filters from the user's query below.
                Respond with ONLY a JSON object (no markdown, no explanation) with these keys:
                "cuisine" (e.g. "Italian", "Chinese", or null if not mentioned),
                "priceRange" (one of "budget", "mid-range", "premium", or null if not mentioned),
                "vibe" (one short word like "quiet", "romantic", "casual", "family-friendly", or null if not mentioned).

                Query: "$query"
            """.trimIndent()

            val requestBody = JSONObject().apply {
                put("contents", listOf(JSONObject().apply {
                    put("parts", listOf(JSONObject().apply { put("text", prompt) }))
                }))
                put("generationConfig", JSONObject().apply {
                    put("response_mime_type", "application/json")
                })
            }

            val connection = URL("$ENDPOINT?key=$apiKey").openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true
            connection.outputStream.use { it.write(requestBody.toString().toByteArray()) }

            val responseCode = connection.responseCode
            val responseText = (if (responseCode in 200..299) connection.inputStream else connection.errorStream)
                .bufferedReader()
                .use { it.readText() }

            if (responseCode !in 200..299) {
                return@withContext Result.failure(Exception("Gemini API error $responseCode: $responseText"))
            }

            val candidateText = JSONObject(responseText)
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")

            val parsed = JSONObject(candidateText)
            Result.success(
                SearchIntent(
                    cuisine = parsed.optStringOrNull("cuisine"),
                    priceRange = parsed.optStringOrNull("priceRange"),
                    vibe = parsed.optStringOrNull("vibe")
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun JSONObject.optStringOrNull(key: String): String? {
        if (isNull(key) || !has(key)) return null
        val value = optString(key)
        return value.takeIf { it.isNotBlank() && !it.equals("null", ignoreCase = true) }
    }
}
