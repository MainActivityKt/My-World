package com.safire.myworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import com.example.compose.MyWorldTheme

class MainActivity : ComponentActivity() {

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
