package com.safire.myworld.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safire.myworld.data.Continent
import com.safire.myworld.data.Country
import com.safire.myworld.data.MyWorldUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class MyWorldViewModel : ViewModel() {
    private val dummyCountry: Country = Country(
        "Empty", "", "", 0 ,33f, "", listOf(""), "", "", "", 0,"", "" );
    private val dummyContinent: Continent = Continent("", 0, 0, 0u, listOf(dummyCountry));

    private val _uiState = MutableStateFlow(MyWorldUiState(dummyCountry, dummyContinent))
    val uiState = _uiState.asStateFlow()
    var selectedCountryIndex = -1

    lateinit var continents: List<Continent>

    fun retrieveContinents(stringJson: String) {

        viewModelScope.launch {
            continents = Json.decodeFromString<List<Continent>>(stringJson)
        }
    }


    fun changeContinent(continent: Continent) {
        _uiState.update { currentState ->
            currentState.copy(selectedContinent = continent)
        }
    }

    fun previousCountry() {
        _uiState.update { currentState ->
            currentState.copy(selectedCountry = _uiState.value.selectedContinent.countries[selectedCountryIndex - 1])
        }
        selectedCountryIndex--
    }

    fun nextCountry() {
        _uiState.update { currentState ->
            currentState.copy(selectedCountry = _uiState.value.selectedContinent.countries[selectedCountryIndex + 1])
        }
        selectedCountryIndex++
    }

    fun previousButtonEnabled() = selectedCountryIndex > 0
    fun nextButtonEnabled() = selectedCountryIndex <  _uiState.value.selectedContinent.countries.lastIndex



    fun changeCountry(country: Country, index: Int) {
        selectedCountryIndex = index
        _uiState.update { currentState ->
            currentState.copy(selectedCountry = country)
        }
    }
}