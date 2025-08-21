package com.safire.myworld.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String,
    val countryCode: String,
    val capital: String,
    val population: Long,
    val area: Float,
    val subregion: String,
    val languages: List<String>,
    val currency: String,
    val callingCode: String,
    val flag: String,
    val gdp: Long,
    val shortDescription: String,
    val description: String

)
