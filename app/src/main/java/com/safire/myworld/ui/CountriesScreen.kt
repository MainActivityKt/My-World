package com.safire.myworld.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.BrushPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose.MyWorldTheme
import com.safire.myworld.IconText
import com.safire.myworld.R
import com.safire.myworld.data.Country

private val dummyCountry = Country(
    "Nigeria",
    "NG",
    "Abuja",
    12005415451,
    454116144f, "West Africa", listOf("English"),
    "Naira", "+234", "\uD83C\uDDF3\uD83C\uDDEC", 0, "Africa's cultural powerhouse and largest economy, renowned for its vibrant music, film industry, and diverse traditions.", "naN"
)

@Composable
fun CountriesScreen(
    countries: List<Country>,
    onCountryClick: (Country) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn (
        contentPadding = PaddingValues(2.dp),
    ) {
        itemsIndexed(countries) { index, country ->
            CountryItem(country, onCountryClick)
        }
    }
}

@Composable
fun CountryItem(
    country: Country,
    onCountryClick: (Country) -> Unit,
    modifier: Modifier = Modifier) {


    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.onPrimaryContainer),
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {

        Box(
            contentAlignment = Alignment.Center,
            propagateMinConstraints = true,
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { onCountryClick(country) })
        ) {

            Image(
                painterResource(R.drawable.traveling),
                contentScale = ContentScale.FillBounds,
                contentDescription = "Simple city background",
                alpha = 0.3f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                // transparency

            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = country.flag,
                    fontSize = 64.sp,

                    )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${country.name} (${country.countryCode})",
                        style = MaterialTheme.typography.headlineLarge,

                    )

                    IconText(
                        painterResource(R.drawable.location),
                        "Subregion",
                        country.subregion,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    IconText(
                        painterResource(R.drawable.home_pin),
                        "Capital",
                        country.capital,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    IconText(
                        painterResource(R.drawable.population),
                        "Population",
                        String.format("%,d", country.population),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    IconText(
                        painterResource(R.drawable.language),
                        "Language",
                        country.languages.joinToString(),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        country.shortDescription,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold

                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun CountriesScreenPreview() {
    MyWorldTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CountriesScreen(List(3){ dummyCountry }, {Country ->})
        }
    }
}