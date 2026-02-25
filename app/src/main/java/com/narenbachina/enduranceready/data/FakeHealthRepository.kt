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
}