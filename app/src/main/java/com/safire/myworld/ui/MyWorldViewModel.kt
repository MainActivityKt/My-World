package com.safire.myworld.ui

import android.content.res.AssetManager
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safire.myworld.data.Continent
import com.safire.myworld.data.Country
import com.safire.myworld.data.MyWorldUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream

private val dummyCountry = Country(
    "New Benia",
    "NB",
    "Beninia",
    0,
    0f, "Oceania", listOf("English"),
    "Bennies", "+123", "👑", 0, "naN", "naN"
)
private val dummyContinent = Continent(
    "West America",
    0, 0, 0u, countries = listOf(dummyCountry)
)

class MyWorldViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyWorldUiState(dummyCountry, dummyContinent))
    val uiState = _uiState.asStateFlow()

    lateinit var continents: List<Continent>

    fun retrieveContinents(stringJson: String) {
        val data = mutableListOf<Continent>()

        viewModelScope.launch {
                data.addAll(Json.decodeFromString<List<Continent>>(stringJson))

        }
        continents = data
    }




    fun changeContinent(continent: Continent) {
        _uiState.update { currentState ->
            currentState.copy(selectedContinent = continent)
        }
    }

    fun changeCountry(country: Country) {
        _uiState.update { currentState ->
            currentState.copy(selectedCountry = country)
        }
    }
}