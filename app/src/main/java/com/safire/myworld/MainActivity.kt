package com.safire.myworld

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultShadowColor
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.MyWorldTheme
import com.example.compose.onPrimaryContainerDark
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

    val drawables = listOf(
        R.drawable.asia_mayon,
        R.drawable.africa_pyramids,
        R.drawable.europe__colloseum,
        R.drawable.north_america_capitol,
        R.drawable.rio_de_janeiro,
        R.drawable.melbourne,
        R.drawable.antaractica_penguins
    )

    Scaffold(
        
        topBar = {
            MyWorldTopBar()
                 },
        ) { innerPadding ->


        LazyVerticalGrid(
            contentPadding = innerPadding,
            columns = GridCells.Fixed(2)
        ) {

            itemsIndexed(continents) { index, continent ->
                ContinentItem(continent, drawables.get(index))
            }
        }

    }
}


@Composable
fun ContinentItem(continent: Continent, @DrawableRes drawableId: Int, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        modifier = Modifier
            .padding(4.dp)
            .size(250.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true,
            modifier = Modifier.fillMaxSize()
        ) {

            Image(
                painterResource(drawableId),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                alpha = 0.35f, // transparency

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
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                IconText(
                    painterResource(R.drawable.area_icon),
                    "Area",
                    String.format("%,d km²", continent.area),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                IconText(
                    painterResource(R.drawable.flag_icon),
                    "Countries",
                    String.format("%d", continent.numberOfCountries.toInt()),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyWorldTopBar(modifier: Modifier = Modifier) {
    TopAppBar(title = {
        Text("My World", style = MaterialTheme.typography.titleMedium, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, letterSpacing = 8.sp)
    }, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ),
        navigationIcon = {
            Image(painterResource(R.drawable.app_icon), contentDescription = "app icon", Modifier.size(32.dp)) }
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