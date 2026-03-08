package com.narenbachina.enduranceready.data

interface HealthRepository {

    suspend fun getSleepHours(): Double?
    suspend fun getRestingHeartRate(): Int?

    suspend fun getLatestWeight(): Double?

    suspend fun getLatestHeight(): Double?

    suspend fun getLatestNutritionCalories(): Double?

    suspend fun getLatestCaloriesBurned(): Double?



}



