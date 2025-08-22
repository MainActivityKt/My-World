package com.safire.myworld.data

import kotlinx.serialization.Serializable

@Serializable
data class Continent(
    val name: String,
    val population: Long,
    val area: Long,
    val numberOfCountries: UInt,
    val countries: List<Country> = emptyList()

)
