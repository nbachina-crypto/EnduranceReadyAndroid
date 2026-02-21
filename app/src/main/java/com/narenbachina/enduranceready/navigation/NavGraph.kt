package com.narenbachina.enduranceready.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.narenbachina.enduranceready.screens.FoodDetailsScreen
import com.narenbachina.enduranceready.screens.HomeScreen
import com.narenbachina.enduranceready.screens.ProfileScreen
import com.narenbachina.enduranceready.screens.ReadinessScoreScreen
import com.narenbachina.enduranceready.screens.SleepDetailsScreen
import com.narenbachina.enduranceready.screens.WorkoutMovementDetailsScreen





/*
NavController and NavHostController refer to the same core navigation
functionality within the Android Navigation component, but NavHostController
is a subclass of 'NavController' that is specifically used in Jetpack Compose
for integrating with a *NavHost* composable.
 */
@Composable
fun NavGraph(navController: NavHostController

){
    NavHost(navController = navController,
        startDestination = NavigationDestination.Home.route
    ) {
        composable(NavigationDestination.Home.route){
            HomeScreen(navController)
        }
        composable(NavigationDestination.FoodDetails.route){
            FoodDetailsScreen()
        }
        composable(NavigationDestination.SleepDetails.route){
            SleepDetailsScreen()
        }
        composable(NavigationDestination.WorkoutDetails.route){
            WorkoutMovementDetailsScreen()
        }
        composable(NavigationDestination.Profile.route){
            ProfileScreen()
        }

        composable(NavigationDestination.ReadinessScoreDetails.route){
            ReadinessScoreScreen(navController)
        }



    }
}








