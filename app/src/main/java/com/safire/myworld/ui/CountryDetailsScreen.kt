package com.safire.myworld.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.compose.MyWorldTheme
import com.safire.myworld.IconText
import com.safire.myworld.R
import com.safire.myworld.data.Country
import kotlinx.coroutines.delay
import java.text.BreakIterator
import java.text.StringCharacterIterator


@Composable
fun CountryDetails(
    selectedCountry: Country,
    previousEnabled: Boolean,
    onPrevious: () -> Unit,
    nextEnabled: Boolean,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {

    val contentColor = MaterialTheme.colorScheme.onSecondaryContainer

    Column(
        modifier = modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors()
                .copy(containerColor = MaterialTheme.colorScheme.secondaryContainer),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.onSecondaryContainer),
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp)
                    .animateContentSize()


            ) {
                Text(
                    text = selectedCountry.flag,
                    fontSize = 102.sp
                )

                Text(
                    text = selectedCountry.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontSize = 52.sp,
                    lineHeight = 1.1.em
                )

                IconText(
                    painterResource(R.drawable.home_pin),
                    "Capital",
                    "Capital city: ${selectedCountry.capital}",
                    color = contentColor,
                    false
                )

                IconText(
                    painterResource(R.drawable.location),
                    "Subregion",
                    "Subregion: ${selectedCountry.subregion}",
                    color = contentColor,
                    false
                )

                val languagesCount = selectedCountry.languages.size
                IconText(
                    painterResource(R.drawable.language),
                    "Language",
                    "Official " + (if (languagesCount < 2) "language: " else "languages: ") + selectedCountry.languages.joinToString(),
                    color = contentColor,
                    false
                )

                IconText(
                    painterResource(R.drawable.population),
                    "Population",
                    "Population: %,d".format(selectedCountry.population),
                    color = contentColor,
                    false

                )

                IconText(
                    painterResource(R.drawable.area_icon),
                    "Area",
                    "Area: %,.1f km²".format(selectedCountry.area),
                    color = contentColor,
                    false
                )

                IconText(
                    painterResource(R.drawable.gdp),
                    "GDP",
                    "GDP: %,d".format(selectedCountry.gdp),
                    color = contentColor,
                    false
                )

                IconText(
                    painterResource(R.drawable.money),
                    "Currency",
                    "Currency: ${selectedCountry.currency}",
                    color = contentColor,
                    false
                )

                IconText(
                    painterResource(R.drawable.call),
                    "Dialing code",
                    "Dialing code: ${selectedCountry.callingCode}",
                    color = contentColor,
                    false
                )
                AnimatedText(selectedCountry.description, 4L)
            }
        }

        Spacer(Modifier
            .weight(1f)
            .fillMaxHeight()
        )

        Row (
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .fillMaxSize()


                .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
        ){
            Button(
                onClick = onPrevious,
                enabled = previousEnabled,
                modifier = Modifier.weight(0.3f)

            ) {
                Text("Previous", fontSize = 20.sp)
            }

            Spacer(Modifier.weight(0.1f))

            Button(
                onClick = onNext,
                enabled = nextEnabled,
                modifier = Modifier.weight(0.3f)
            ) {
                Text("Next", fontSize = 20.sp)
            }

        }
    }


}


@Composable
private fun AnimatedText(
    text: String,
    typingDelayInMs: Long
) {

    // Use BreakIterator as it correctly iterates over characters regardless of how they are
    // stored, for example, some emojis are made up of multiple characters.
    // You don't want to break up an emoji as it animates, so using BreakIterator will ensure
    // this is correctly handled!
    val breakIterator = remember(text) { BreakIterator.getCharacterInstance() }

    // Define how many milliseconds between each character should pause for. This will create the
    // illusion of an animation, as we delay the job after each character is iterated on.

    var substringText by remember {
        mutableStateOf("")
    }
    LaunchedEffect(text) {
        // Initial start delay of the typing animation
        //delay(700)
        breakIterator.text = StringCharacterIterator(text)

        var nextIndex = breakIterator.next()
        // Iterate over the string, by index boundary
        while (nextIndex != BreakIterator.DONE) {
            substringText = text.subSequence(0, nextIndex).toString()
            // Go to the next logical character boundary
            nextIndex = breakIterator.next()
            delay(typingDelayInMs)
        }
    }
    Text(substringText, fontSize = 20.sp)
}

@Preview
@Composable
private fun CountryDetailsPreviewLightMode() {

    val dummyCountry = Country(
        "Nigeria",
        "NG",
        "Abuja",
        12005415451,
        4541164f,
        "West Africa",
        listOf("English"),
        "Naira",
        "+234",
        "\uD83C\uDDF3\uD83C\uDDEC",
        0,
        "Africa's cultural powerhouse and largest economy, renowned for its vibrant music, film industry, and diverse traditions.",
        "naN"
    )

    MyWorldTheme {
        Scaffold { contentPadding ->
            CountryDetails(
                dummyCountry,
                true, {}, true, {}
            )
        }

    }
}