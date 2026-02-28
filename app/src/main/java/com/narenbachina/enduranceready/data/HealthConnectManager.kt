package com.narenbachina.enduranceready.data

import android.content.Context
import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.PermissionController
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.RestingHeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import java.time.Instant

class HealthConnectManager(
    private val context: Context
){
    private val healthConnectClient by lazy {
        HealthConnectClient.getOrCreate(context)
    }
    val permissions=setOf(
        HealthPermission.getReadPermission(WeightRecord::class),
        HealthPermission.getReadPermission(HeightRecord::class),
        HealthPermission.getReadPermission(RestingHeartRateRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class)
    )

    fun isAvailable(): Boolean{
        return HealthConnectClient.getSdkStatus(context)==
                HealthConnectClient.Companion.SDK_AVAILABLE
    }
    //TODO: check the need for permissions paramter or remove it
    suspend fun hasAllPermissions(permissions: Set<String>): Boolean{
        return healthConnectClient.permissionController.getGrantedPermissions()
            .containsAll(permissions)
    }
//TODO: check wether we should keep this function in this file or move it to UI
    fun requestPermissionsActivityContract(): ActivityResultContract<Set<String>, Set<String>> {
        return PermissionController.createRequestPermissionResultContract()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun readLatestWeight(): WeightRecord?{
        val request=ReadRecordsRequest(
            recordType = WeightRecord::class,
            timeRangeFilter = TimeRangeFilter.after(Instant.EPOCH),
            pageSize = 1

        )
        val response=healthConnectClient.readRecords(request)

        return response.records.firstOrNull()
    }
}