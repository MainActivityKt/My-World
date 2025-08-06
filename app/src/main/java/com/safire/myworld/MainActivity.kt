package com.safire.myworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.MyWorldTheme
import com.safire.myworld.model.Continent
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
fun MyWorldApp(continents: List<Continent>, modifier: Modifier = Modifier) {

    val drawables = listOf(R.drawable.yogyakarta)

    Scaffold(modifier.fillMaxSize()) { innerPadding ->


        LazyVerticalGrid(
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
            columns = GridCells.Fixed(2)
        ) {

            items(continents) {
                ContinentItem(it, modifier = Modifier.padding(innerPadding))
            }
        }

    }
}


@Composable
fun ContinentItem(continent: Continent, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        modifier = modifier
            .padding(4.dp)
            .size(250.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painterResource(R.drawable.yogyakarta),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                alpha = 0.3f, // transparency
            )

            Text(
                continent.name,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.Center,

                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 4.dp)

            )



            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)

            ) {
                IconText(
                    painterResource(R.drawable.population),
                    "Population",
                    String.format("%,d", continent.population),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )

                IconText(
                    painterResource(R.drawable.area_icon),
                    "Area",
                    String.format("%,d km²", continent.area),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )

                IconText(
                    painterResource(R.drawable.flag_icon),
                    "Countries",
                    String.format("%d", continent.numberOfCountries.toInt()),
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
        }
    }
}

@Composable
fun IconText(
    icon: Painter,
    iconDescription: String,
    text: String,
    color: Color,
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(4.dp)) {
        Icon(
            icon,
            contentDescription = iconDescription,
            tint = color
        )
        Text(
            text,
            color = color,
            style = MaterialTheme.typography.titleMedium
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