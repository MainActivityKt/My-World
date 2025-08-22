package com.safire.myworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.MyWorldTheme
import com.safire.myworld.data.Continent
import com.safire.myworld.ui.ContinentsScreen
import com.safire.myworld.ui.MyWorldViewModel
import com.safire.myworld.ui.Screen
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
    continents.addAll(Json.decodeFromString<List<Continent>>(jsonString))

    return continents
}

@Composable
fun MyWorldApp(
    continents: List<Continent>,
    viewModel: MyWorldViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    val drawables = listOf(
        R.drawable.asia_mayon,
        R.drawable.africa_pyramids,
        R.drawable.melbourne,
        R.drawable.europe__colloseum,
        R.drawable.north_america_capitol,
        R.drawable.rio_de_janeiro,
        R.drawable.antaractica_penguins
    )
    Scaffold(

        topBar = {
            MyWorldTopBar()
        },
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = Screen.CONTINENTS_LIST.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.CONTINENTS_LIST.name) {
                ContinentsScreen(
                    continents, drawables, onContinentClick = { it ->
                        viewModel.changeContinent(it)
                        navController.navigate(Screen.COUNTRIES_LIST.name)
                    }
                )
            }

            composable(Screen.COUNTRIES_LIST.name) {
                Column (Modifier.fillMaxSize()){
                    Text(
                        "This continent has ${uiState.selectedContinent.numberOfCountries} countries",
                        fontSize = 22.sp
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorldTopBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = {
            Text(
                "My World",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 25.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 8.sp
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            Image(
                painterResource(R.drawable.app_icon),
                contentDescription = "app icon",
                Modifier.size(32.dp)
            )
        }
    )
}

@Composable
fun IconText(
    icon: Painter,
    iconDescription: String,
    text: String,
    color: Color,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(8.dp)) {
        Icon(
            icon,
            contentDescription = iconDescription,
            tint = color
        )
        Text(
            text,
            color = color,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleMedium.copy(

                shadow = Shadow(Color.White, offset = Offset(4f, 5f), blurRadius = 16f),

                ),
            fontSize = 16.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {

    val dummyContinent = Continent(
        "South East Asia",
        4835320000,
        44579000,
        49U,
    )

    val dummyList = List(7) { dummyContinent }

    MyWorldTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MyWorldApp(dummyList)
        }
    }
}