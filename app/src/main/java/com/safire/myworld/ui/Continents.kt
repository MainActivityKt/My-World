package com.safire.myworld.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.safire.myworld.IconText
import com.safire.myworld.R
import com.safire.myworld.data.Continent

@Composable
fun ContinentsScreen(
    continents: List<Continent>,
    drawables: List<Int>,
    onContinentClick: (Continent) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        contentPadding = PaddingValues(2.dp),
        columns = GridCells.Fixed(2)
    ) {
        itemsIndexed(continents) { index, continent ->
            ContinentItem(
                continent,
                drawables[index],
                onContinentClick = onContinentClick

            )
        }
    }
}

@Composable
fun ContinentItem(
    continent: Continent,
    @DrawableRes drawableId: Int,
    onContinentClick: (Continent) -> Unit,
    modifier: Modifier = Modifier
) {
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
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { onContinentClick(continent) })
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
                lineHeight = 1.2.em,
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
