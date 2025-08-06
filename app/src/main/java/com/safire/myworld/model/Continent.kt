package com.safire.myworld.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable

@Serializable
data class Continent(
    val name: String,
    val population: Long,
    val area: Long,
    val numberOfCountries: UInt,
    val countries: List<Country> = emptyList()

)
