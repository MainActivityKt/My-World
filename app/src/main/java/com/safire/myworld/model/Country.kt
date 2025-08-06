package com.safire.myworld.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val name: String? = null,
    val capital: String? = null
    // Add other fields later
)
