package com.narenbachina.enduranceready.data

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.coroutines.delay
import kotlin.time.Duration

/**
 * HealthRepositoryImplementation
 *
 * ROLE:
 * Acts as the data abstraction layer between ViewModel and HealthConnectManager.
 *
 * RESPONSIBILITY:
 * - Exposes clean, simplified data models to the ViewModel
 * - Hides low-level HealthConnect API details
 * - Converts HealthRecord objects into primitive domain values (Double, Int, etc.)
 *
 * ARCHITECTURE POSITION:
 * UI → ViewModel → Repository → HealthConnectManager → HealthConnect API
 *
 * WHY THIS LAYER EXISTS:
 * 1. Keeps ViewModel independent from Health Connect SDK
 * 2. Makes testing easier (can swap with FakeHealthRepository)
 * 3. Centralizes data transformation logic
 * 4. Allows future changes without breaking ViewModel
 */
class HealthRepositoryImplementation(
    private val healthConnectManager: HealthConnectManager
) : HealthRepository {


    override suspend fun getSleepHours(): Double {
        val record=healthConnectManager.readLatestSleepSession()?:return 0.0

        val duration=java.time.Duration.between(record.startTime,record.endTime)

        return duration.toMinutes()/60.0
    }


    override suspend fun getRestingHeartRate(): Int? {
        val record=healthConnectManager.readLatestRestingHeartRate()

        return record?.beatsPerMinute?.toInt()
    }

    /**
     * Returns latest weight in kilograms.
     *
     * PROCESS:
     * 1. Calls HealthConnectManager to read latest WeightRecord
     * 2. Extracts weight value
     * 3. Converts to kilograms
     *
     * RETURNS:
     * - Double → weight in kg
     * - null   → no weight data available
     *
     * WHY repository converts here:
     * ViewModel should not deal with WeightRecord object.
     * It should only receive primitive domain values.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getLatestWeight(): Double? {

        val record = healthConnectManager.readLatestWeight()

        return record?.weight?.inKilograms
    }

    override suspend fun getLatestHeight(): Double? {
        val record=healthConnectManager.readLatestHeight()

        return record?.height?.inFeet
    }

    override suspend fun getLatestNutritionCalories(): Double? {
       return healthConnectManager.readYesterdayNutritionCalories()
    }

    override suspend fun getLatestCaloriesBurned(): Double? {
        return healthConnectManager.readYesterdayCaloriesBurned()

    }
}