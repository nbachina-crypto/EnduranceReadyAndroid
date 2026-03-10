package com.narenbachina.enduranceready.readiness

import kotlin.math.abs

class ScoreNormalizer {

    //TODO Decimals??
     fun sleepScore(actualSleep: Double, targetSleep: Double): Double {
        val score = (actualSleep / targetSleep) * 100

        return when {
            score > 100.0 -> 100.0
            score < 0.0 -> 0.0
            else -> score
        }
    }


    fun heartScore(restingHeartRate: Int): Double {
        return when {
            restingHeartRate <= 60 -> 100.0
            restingHeartRate <= 65 -> 90.0
            restingHeartRate <= 75 -> 80.0
            restingHeartRate <= 85 -> 70.0
            restingHeartRate <= 95 -> 60.0
            restingHeartRate <= 105 -> 50.0
            restingHeartRate <= 115 -> 40.0
            else -> 30.0
        }
    }


    fun energyScore(
        caloriesBurned: Double,
        caloriesConsumed: Double,
        caloriesGoal: GoalType,
        targetOffset:Int=300
    ): Double{
        val balance=caloriesBurned-caloriesConsumed

        val targetBalance=when(caloriesGoal) {
            GoalType.DEFICIT -> targetOffset
            GoalType.MAINTENANCE -> 0
            GoalType.SURPLUS -> -targetOffset
        }

        val deviation= abs(balance - targetBalance)

        return when{
            deviation <= 200 -> 100.00
            deviation <= 400 -> 80.00
            deviation <= 700 -> 60.00
            else -> 40.00
        }


    }

    fun strainScore(caloriesBurned: Double): Double{

        return when {
            caloriesBurned <= 2000 -> 100.0
            caloriesBurned <= 2500 -> 85.0
            caloriesBurned <= 3500 -> 70.0
            caloriesBurned <= 4000 -> 55.0
            caloriesBurned <= 4500 -> 40.0
            else -> 30.0
        }
    }

}


enum class GoalType{
    DEFICIT,
    MAINTENANCE,
    SURPLUS
}