package com.safire.myworld.ui

import androidx.lifecycle.ViewModel
import com.safire.myworld.data.Continent
import com.safire.myworld.data.Country
import com.safire.myworld.data.MyWorldUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet

private val dummyCountry = Country(
    "New Benia",
    "NB",
    "Beninia",
    0,
    0f, "West America", listOf("English"),
    "Bennie", "+123", "💡", 0, "naN", "naN"
)
private val dummyContinent = Continent(
    "West America",
    0, 0, 0u, countries = listOf(dummyCountry)
)

class MyWorldViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyWorldUiState(dummyCountry, dummyContinent))
    val uiState = _uiState.asStateFlow()

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