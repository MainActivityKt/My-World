package com.safire.myworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.compose.MyWorldTheme
import com.safire.myworld.data.Continent
import kotlinx.serialization.json.Json
import java.io.InputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val continents = retrieveContinents(assets.open("data.json"))
        setContent {
            MyWorldTheme {
                MyWorldApp(continents)
            }
        }
    }
}

private fun retrieveContinents(file: InputStream): List<Continent> {
    val continents = mutableListOf<Continent>()

    val jsonString = file.bufferedReader().use { it.readText() }
    val json = Json{ ignoreUnknownKeys = true }
    continents.addAll(json.decodeFromString<List<Continent>>(jsonString))
    return continents
}