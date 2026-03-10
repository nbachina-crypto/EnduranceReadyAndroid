package com.narenbachina.enduranceready.readiness

class ReadinessCalculatorLogic {

    private val normalizer = ScoreNormalizer()

    fun calculateReadiness(
        sleepHours: Double,
        idealSleep: Double,
        restingHR: Int,
        caloriesConsumed: Double,
        caloriesBurned: Double,
        goalType: GoalType
    ): ReadinessResult {

        val sleepScore = normalizer.sleepScore(
            actualSleep = sleepHours,
            targetSleep = idealSleep
        )

        val heartScore = normalizer.heartScore(restingHR)

        val energyScore = normalizer.energyScore(
            caloriesBurned = caloriesBurned,
            caloriesConsumed = caloriesConsumed,
            caloriesGoal = goalType
        )

        val strainScore = normalizer.strainScore(caloriesBurned)

        val recoveryScore = (0.5 * sleepScore) + (0.3 * heartScore) + (0.2 * energyScore)

        val readiness = (0.7 * recoveryScore) + (0.3 * strainScore)

        val finalScore = readiness.coerceIn(0.0, 100.0).toInt()

        val status = determineStatus(finalScore)

        val insights = generateInsights(
            sleepScore,
            heartScore,
            energyScore,
            strainScore
        )

        return ReadinessResult(
            score = finalScore,
            status = status,
            insights = insights
        )
    }

    private fun determineStatus(score: Int): String {
        return when {
            score >= 85 -> "Peak readiness"
            score >= 70 -> "Good readiness"
            score >= 50 -> "Moderate readiness"
            score >= 30 -> "Low readiness"
            else -> "Recovery recommended"
        }
    }

    private fun generateInsights(
        sleepScore: Double,
        heartScore: Double,
        energyScore: Double,
        strainScore: Double
    ): List<String> {

        val insights = mutableListOf<String>()

        if (sleepScore < 70) {
            insights.add("Sleep below target")
        }

        if (heartScore < 70) {
            insights.add("Elevated resting heart rate")
        }

        if (energyScore < 70) {
            insights.add("Energy intake not aligned with goal")
        }

        if (strainScore < 60) {
            insights.add("High training load yesterday")
        }

        if (insights.isEmpty()) {
            insights.add("All recovery indicators look good")
        }

        return insights
    }
}

data class ReadinessResult(
    val score: Int,
    val status: String,
    val insights: List<String>
)