package com.narenbachina.enduranceready.model


/*

D)Why we use = instead of : for default values

In Kotlin, the syntax for defining properties in a data class
follows a strict pattern:val name: Type = DefaultValue
 */
data class ReadinessUiState(
    val sleepHours:Double?=null,
    val restingHeartRate:Int?=null,
    val error: String?=null,
    val isLoading: Boolean=true,
)