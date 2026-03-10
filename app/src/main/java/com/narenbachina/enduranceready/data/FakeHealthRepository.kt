package com.narenbachina.enduranceready.data

import kotlinx.coroutines.delay

class FakeHealthRepository(): HealthRepository{
    override suspend fun getSleepHours(): Double {
        delay(1000)
        return 7.2
    }

    override suspend fun getRestingHeartRate(): Int {
        delay(1000)
        return 65
    }

    override suspend fun getLatestWeight(): Double? {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestHeight(): Double? {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestNutritionCalories(): Double? {
        TODO("Not yet implemented")
    }

    override suspend fun getLatestCaloriesBurned(): Double? {
        TODO("Not yet implemented")
    }
}