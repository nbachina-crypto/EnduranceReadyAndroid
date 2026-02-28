package com.narenbachina.enduranceready.data

interface HealthRepository {

    suspend fun getSleepHours(): Double
    suspend fun getRestingHeartRate(): Int

    suspend fun getLatestWeight(): Double?


}



