package com.narenbachina.enduranceready.navigation

/**
 * Represents all top-level navigation destinations in the app.
 *
 * This sealed class acts as a single source of truth for navigation routes.
 * Each object corresponds to exactly one screen and defines its unique route.
 *
 * Used Object as Each destination should exist exactly once and
 * object = singleton → no duplication
 *
 * All obejcts inherit from NavigationDestination and implement its parameters
 */
sealed class NavigationDestination(val route: String) {

    /** Home screen - daily overview and insights */
    object Home : NavigationDestination("Home")

    /** Food details and nutrition tracking */
    object FoodDetails : NavigationDestination("Nutrition")

    /** Sleep details and recovery tracking */
    object SleepDetails : NavigationDestination("Sleep")

    /** Workout / movement tracking */
    object WorkoutDetails : NavigationDestination("Workout/Movement")

    /** Readiness score analysis screen */
    object ReadinessScoreDetails : NavigationDestination("Readiness Score")

    /** User profile and settings */
    object Profile : NavigationDestination("Profile")

}


