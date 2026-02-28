package com.narenbachina.enduranceready.data

import android.os.Build
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.RequiresApi
import androidx.health.connect.client.PermissionController
import kotlinx.coroutines.delay

class HealthRepositoryImplementation(
    private val healthConnectManager: HealthConnectManager
): HealthRepository {
    //TODO

    override suspend fun getSleepHours(): Double {
        delay(1000)
        return 7.2
    }

    //TODO
    override suspend fun getRestingHeartRate(): Int {
        delay(1000)
        return 65
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getLatestWeight(): Double? {
        val record=healthConnectManager.readLatestWeight()

        return record?.weight?.inKilograms
    }


}