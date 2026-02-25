package com.narenbachina.enduranceready

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.narenbachina.enduranceready.data.AppContainer
import com.narenbachina.enduranceready.navigation.BottomNavigationBar
import com.narenbachina.enduranceready.navigation.NavGraph
import com.narenbachina.enduranceready.navigation.NavigationDestination
import com.narenbachina.enduranceready.navigation.TopNavigationBar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnduranceReadyApp(){
    val navController=rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute=navBackStackEntry?.destination?.route




    Scaffold(
        topBar = {
            if(currentRoute!=(NavigationDestination.Home.route)&&currentRoute!=(NavigationDestination.Profile.route)){
                TopNavigationBar(presentScreenHeading =currentRoute?:"" , onBackClick = {
                    navController.navigate(NavigationDestination.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                }
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(navController=navController)
        }
    ){
        innerPadding->

        Box(modifier = Modifier.padding(innerPadding)){
            val container=remember { AppContainer() }
            NavGraph(navController=navController,container=container)
        }


    }
}