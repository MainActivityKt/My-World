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
    var selectedCountry: Country = Country(
        "Empty", "", "", 0 ,33f, "", listOf(""), "", "", "", 0,"", "" );
    var selectedContinent: Continent = Continent("", 0, 0, 0u, listOf(selectedCountry));
    private val _uiState = MutableStateFlow(MyWorldUiState(selectedCountry, selectedContinent))
    val uiState = _uiState.asStateFlow()

    lateinit var continents: List<Continent>

    fun retrieveContinents(stringJson: String) {

        viewModelScope.launch {
            continents = Json.decodeFromString<List<Continent>>(stringJson)
            selectedContinent = continents.first()
            selectedCountry = selectedContinent.countries.first()
        }
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