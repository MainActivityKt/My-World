package com.safire.myworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.compose.MyWorldTheme
import com.safire.myworld.data.Continent

class MainActivity : ComponentActivity() {

    val isLoading = mutableStateOf(true)
    lateinit var continents: MutableList<Continent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val stringJson = assets.open("data.json").bufferedReader().use { it.readText() }
        setContent {
            MyWorldTheme {
                MyWorldApp(stringJson)
            }
        }
    }
}
