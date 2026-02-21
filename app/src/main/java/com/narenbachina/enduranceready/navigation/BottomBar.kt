package com.narenbachina.enduranceready.navigation

import android.net.http.SslCertificate.saveState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.narenbachina.enduranceready.R

/**
 * Represents a single item in the bottom navigation bar.
 *
 * This is a UI model nor a navigation destination.
 * It maps visual properties (title, icon) to a particular
 * navigation destination within the bottom bar
 */
data class BottomBarNavigationItem(
    val title: String,        //label shown in bottom bar
    val iconResourceID: Int,  //Icon shown in bottom bar
    val route: String         //Navigation destination/route
)




val bottomBarItemsList= listOf(
//List of all top-level destinations shown in the bottom navigation bar.
    BottomBarNavigationItem(
        title = "Home",
        iconResourceID = R.drawable.homeicon,
        route = NavigationDestination.Home.route

    ),BottomBarNavigationItem(
        title = "ReadinessScore",
        iconResourceID = R.drawable.readinessscore,  //TODO: replace with correct icon
        route = NavigationDestination.ReadinessScoreDetails.route

    ),BottomBarNavigationItem(
        title = "Nutrition",
        iconResourceID = R.drawable.foodicon,//TODO: replace with correct icon
        route = NavigationDestination.FoodDetails.route
    ),BottomBarNavigationItem(
        title = "Sleep",
        iconResourceID = R.drawable.sleepicon  //TODO: replace with correct icon
        ,route = NavigationDestination.SleepDetails.route

    ),BottomBarNavigationItem(
        title = "Workout",
        iconResourceID = R.drawable.exerciseicon,//TODO: replace with correct icon
        route = NavigationDestination.WorkoutDetails.route

    ),BottomBarNavigationItem(
        title = "Profile",
        iconResourceID = R.drawable.profile,
        route = NavigationDestination.Profile.route

    )
)

@Composable
fun BottomNavigationBar(navController: NavHostController){
    NavigationBar(
    ) {

/*
Observes the NavController back stack and Re-composes BottomBar when
navigation changes
 */
        val navBackStackEntry by navController.currentBackStackEntryAsState()

/*
current route of visible screen,it is used to determine
which icon is selected
 */

        val currentRoute=navBackStackEntry?.destination?.route
/**
 * * Create one NavigationBarItem for each bottom bar entry.
 */
        bottomBarItemsList.forEach { item ->
            NavigationBarItem(
                selected = currentRoute==item.route, // Visual active state
                onClick = {
                    navController.navigate(item.route) {// Triggers navigation to the selected route

                        popUpTo(navController.graph.startDestinationId){
                            saveState=true
                        }

                        launchSingleTop=true

                        restoreState=true
                    }
                },
                icon = {
                    Icon(painter = painterResource(id = item.iconResourceID), contentDescription = item.title)
                },
                label = {
                    Text(text = item.title,
                    )
                }, colors = NavigationBarItemColors(
                    selectedIconColor= MaterialTheme.colorScheme.primary,
                    selectedTextColor=MaterialTheme.colorScheme.primary,
                    unselectedIconColor= MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor=MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIconColor=Color.Gray,
                    disabledTextColor=Color.Gray,
                    selectedIndicatorColor=MaterialTheme.colorScheme.primary

                )
                , alwaysShowLabel = false
            )


        }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBar(){

}