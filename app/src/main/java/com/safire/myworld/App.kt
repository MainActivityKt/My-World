package com.safire.myworld

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
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
import com.safire.myworld.data.Country
import com.safire.myworld.data.MyWorldUiState
import com.safire.myworld.ui.ContinentsScreen
import com.safire.myworld.ui.CountriesScreen
import com.safire.myworld.ui.CountryDetails
import com.safire.myworld.ui.MyWorldViewModel
import com.safire.myworld.ui.Screen


@Composable
fun MyWorldApp(
    stringJson: String,
    viewModel: MyWorldViewModel = viewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    viewModel.retrieveContinents(stringJson)

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


        val emptyCountriesToast = Toast.makeText(
            LocalContext.current,
            "No countries in this continent",
            Toast.LENGTH_LONG
        )

        NavHost(
            navController = navController,
            startDestination = Screen.CONTINENTS_LIST.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.CONTINENTS_LIST.name) {
                ContinentsScreen(
                    viewModel.continents, drawables, onContinentClick = { it ->

                        if (it.numberOfCountries > 0u) {
                            viewModel.changeContinent(it)
                            navController.navigate(Screen.COUNTRIES_LIST.name)
                        } else {
                            emptyCountriesToast.show()
                        }

                    }
                )
            }

            composable(Screen.COUNTRIES_LIST.name) {
                CountriesScreen(
                    uiState.selectedContinent.countries,
                    { country, index ->
                        viewModel.changeCountry(country, index)

                        navController.navigate(Screen.COUNTRY_DETAILS.name)

                    })
            }
            composable(Screen.COUNTRY_DETAILS.name) {
                CountryDetails(
                    selectedCountry = uiState.selectedCountry,
                    previousEnabled = viewModel.previousButtonEnabled(),
                    onPrevious = { viewModel.previousCountry() },
                    nextEnabled = viewModel.nextButtonEnabled(),
                    onNext = { viewModel.nextCountry() },
                )
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
        }, modifier = Modifier
    )
}

@Composable
fun IconText(
    icon: Painter,
    iconDescription: String,
    text: String,
    color: Color,
    addShadow: Boolean = true
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),

        ) {
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
                shadow = if (addShadow) Shadow(
                    Color.White,
                    offset = Offset(4f, 5f),
                    blurRadius = 16f
                ) else Shadow(),
            ),
            fontSize = 20.sp,
        )
    }
}

// Special version of the app's screen for previews, with viewmodel removed
@Composable
fun MyWorldApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {

    val dummyCountry = Country(
        "New Benia",
        "NB",
        "Beniston",
        12005415451,
        3521144f,
        "Central America",
        listOf("English"),
        "Bennies",
        "+000",
        "\uD83D\uDC51",
        0,
        "A kingdom run by Ben, from Viva la dirt League.",
        "naN"
    )

    val dummyContinent = Continent(
        "Central America",
        1234567489,
        123456,
        12u,
        countries = List(12) { dummyCountry }
    )

    val continents = List(7) { dummyContinent }


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
        var uiState = MyWorldUiState(continents[0].countries.first(), continents.first())

        val emptyCountriesToast = Toast.makeText(
            LocalContext.current,
            "Zero countries in this continent",
            Toast.LENGTH_SHORT
        )

        NavHost(
            navController = navController,
            startDestination = Screen.CONTINENTS_LIST.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.CONTINENTS_LIST.name) {
                ContinentsScreen(
                    continents, drawables, onContinentClick = { it ->
                        if (it.numberOfCountries > 0u) {
                            uiState = uiState.copy(selectedContinent = it)
                            navController.navigate(Screen.COUNTRIES_LIST.name)
                        } else {
                            emptyCountriesToast.show()
                        }


                    }
                )
            }

            composable(Screen.COUNTRIES_LIST.name) {
                CountriesScreen(uiState.selectedContinent.countries, { country, index ->
                    uiState = uiState.copy(selectedCountry = country)
                    navController.navigate(Screen.COUNTRY_DETAILS.name)

                })
            }
            composable(Screen.COUNTRY_DETAILS.name) {

            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AppPreviewLightMode() {

    MyWorldTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MyWorldApp()
        }
    }
}


@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun AppPreviewDarkMode() {
    MyWorldTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MyWorldApp()
        }
    }
}