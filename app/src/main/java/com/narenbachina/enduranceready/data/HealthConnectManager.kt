package com.narenbachina.enduranceready.data

import android.content.Context
import androidx.activity.result.contract.ActivityResultContract
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.NutritionRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant

/**
 * HealthConnectManager
 *
 * ROLE:Low-level data source responsible for interacting directly with
 * the Health Connect API.
 *
 * RESPONSIBILITIES:
 * - Creating and managing HealthConnectClient(Android Jetpack API entry point for reading, writing, and managing user health/fitness data securely on-device)
 * - Defining required Health Connect permissions
 * - Checking SDK availability
 * - Checking granted permissions
 * - Providing permission request contract
 * - Reading health records from Health Connect
 *
 * ARCHITECTURE POSITION:
 * UI → ViewModel → Repository → HealthConnectManager → HealthConnect API
 */

class HealthConnectManager(
    private val context: Context
) {

    /**
     * Lazily initialized HealthConnectClient.
     *
     * getOrCreate() ensures a single client instance is used
     * for interacting with Health Connect on this device.
     *
     * This client performs all read/write operations.
     */
    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }

    /**
     * Set of all permissions required by the application.
     *
     * These are READ permissions for different health data types.
     *
     * These Permissions must also be declared in AndroidManifest.xml.
     */
    val permissions = setOf(
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(HeightRecord::class),
        HealthPermission.getReadPermission(RestingHeartRateRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getReadPermission(NutritionRecord::class)
    )

    /**
     * Checks whether Health Connect SDK is available on the device.
     *
     * Returns:
     * true  → Health Connect is installed and usable.
     * false → Not available on this device.
     *
     * Used to check before attempting permission requests
     * or reading records.
     */
    fun isAvailable(): Boolean {
        return HealthConnectClient.getSdkStatus(
            context,
            "com.google.android.apps.healthdata"
        ) == HealthConnectClient.SDK_AVAILABLE
    }

    /**
     * Checks whether ALL required permissions are granted.
     *
     * This is a suspend function because it queries the Health Connect
     * permission controller asynchronously.
     *
     * Should be called BEFORE attempting to read any records.
     */
    suspend fun hasAllPermissions(permissions: Set<String>): Boolean {
        return healthConnectClient.permissionController
            .getGrantedPermissions()
            .containsAll(permissions)
    }

    /**
     * Provides an ActivityResultContract used to request
     * Health Connect permissions.
     *
     * This contract must be launched from the UI layer using
     * rememberLauncherForActivityResult.
     *
     * IMPORTANT:
     * Permission request logic should live in the UI layer,
     * not in ViewModel or Repository.
     */
    fun requestPermissionsActivityContract():
            ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }



    /**
     * Reads the latest WeightRecord from Health Connect.
     *
     * REQUIREMENTS:
     * - API 26+ (uses java.time.Instant)
     * - Permission: READ_WEIGHT must be granted
     *
     * HOW IT WORKS:
     * - Creates a ReadRecordsRequest
     * - Filters records from the beginning of time (Instant.EPOCH)
     * - Limits result to 1 record
     *
     * NOTE:
     * pageSize = 1 does NOT guarantee newest record.
     * Health Connect returns records sorted by time descending by default,
     * so firstOrNull() gives the most recent record.
     *
     * RETURNS:
     * - WeightRecord if available
     * - null if no weight data exists
     *
     * WHY suspend?
     * Reading records is an I/O operation and must not block
     * the main (UI) thread.
     */

    suspend fun readLatestWeight(): WeightRecord? {

        val request = ReadRecordsRequest(
            recordType = WeightRecord::class,
            timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH),
            pageSize = 1,
            ascendingOrder = false
        )

        val response = healthConnectClient.readRecords(request)

        return response.records.firstOrNull()
    }


    suspend fun readLatestHeight(): HeightRecord? {

        val request = ReadRecordsRequest(
            recordType = HeightRecord::class,
            timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH),
            pageSize = 1,
            ascendingOrder = false

        )

        val response = healthConnectClient.readRecords(request)

        return response.records.firstOrNull()
    }
    suspend fun readLatestSleepSession(): SleepSessionRecord?{
        val request=ReadRecordsRequest(
            recordType = SleepSessionRecord::class,
            timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH),
            pageSize = 1,
            ascendingOrder = false

        )

        val response=healthConnectClient.readRecords(request)

        return response.records.firstOrNull()
    }


    suspend fun readLatestRestingHeartRate(): RestingHeartRateRecord?{
        val request=ReadRecordsRequest(
            recordType = RestingHeartRateRecord::class,
            timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH),
            pageSize = 1,
            ascendingOrder = false

        )

        val response=healthConnectClient.readRecords(request)

        return response.records.firstOrNull()

    }


    suspend fun readYesterdayCaloriesBurned(): Double{
        val (startTime,endTime)=getYesterdayTimeRange()
        val request=ReadRecordsRequest(
            recordType = TotalCaloriesBurnedRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime,endTime)
        )

        val response=healthConnectClient.readRecords(request)

        return response.records.sumOf {
            it.energy.inKilocalories
        }

    }


    suspend fun readYesterdayNutritionCalories(): Double{
        val (startTime,endTime)=getYesterdayTimeRange()

        val request=ReadRecordsRequest(
            recordType = NutritionRecord::class,
            timeRangeFilter = TimeRangeFilter.between(startTime,endTime)
        )

        val response=healthConnectClient.readRecords(request)

        return response.records.sumOf {
            it.energy?.inKilocalories ?: 0.0
        }

    }

    private fun getYesterdayTimeRange(): Pair<Instant, Instant>{
        val zoneId=java.time.ZoneId.systemDefault()

        val todayStart=java.time.LocalDate.now().atStartOfDay(zoneId).toInstant()

        val yesterdayStart=todayStart.minus(java.time.Duration.ofDays(1))

        return Pair(yesterdayStart,todayStart)


    }




}