package com.narenbachina.enduranceready.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.narenbachina.enduranceready.R
import com.narenbachina.enduranceready.navigation.NavigationDestination


@Composable
fun HomeScreen(navController: NavController){

    LazyColumn(modifier = Modifier.fillMaxSize()
        .padding(vertical = 8.dp, horizontal = 8.dp)

    ) {
        item {
            GreetingCard("Naren","You are struggling with sleep lately,You should focus on sleep", onSeeAnalysisClick = {
                navController.navigate(NavigationDestination.ReadinessScoreDetails.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }

                    launchSingleTop=true

                    restoreState=true
                }

            })

        }
        item {
            StatsCard("Sleep","6.5","hrs",R.drawable.sleepicon, onStatCardClick = {
                navController.navigate(NavigationDestination.SleepDetails.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }

                    launchSingleTop=true

                    restoreState=true
                }

            })
        }
        item {
            StatsCard("Workout","2000","Kcal",R.drawable.exerciseicon,onStatCardClick = {
                navController.navigate(NavigationDestination.WorkoutDetails.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }

                    launchSingleTop=true

                    restoreState=true
                }

            })
        }
        item {
            StatsCard("Nutriton","3000","calories",R.drawable.foodicon,onStatCardClick = {
                navController.navigate(NavigationDestination.FoodDetails.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }

                    launchSingleTop=true

                    restoreState=true
                }

            })
        }

    }



}

@Composable
fun GreetingCard(username:String,sugesstion: String,onSeeAnalysisClick:()-> Unit){
    Card(colors = CardDefaults.cardColors(
        containerColor= MaterialTheme.colorScheme.surfaceVariant
    ), modifier = Modifier.fillMaxWidth().wrapContentHeight().padding(4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text ="Hi $username !! "+"$sugesstion", fontStyle=FontStyle.Italic, fontSize = 30.sp )

            FilledTonalButton(onClick = onSeeAnalysisClick,
                colors = ButtonDefaults.filledTonalButtonColors(Color.LightGray)
                ) {
                Text("See detailed Analysis")
            }
        }
    }
}

@Composable
fun StatsCard(title:String,value:String,unit:String,icon:Int,onStatCardClick:()-> Unit){

    Card(
        colors = CardDefaults.cardColors(
            containerColor= MaterialTheme.colorScheme.surfaceVariant
        ), modifier = Modifier.fillMaxWidth().height(150.dp)
            .padding(4.dp)
        , border = BorderStroke(width = 2.dp, color = Color.Gray), onClick = onStatCardClick

    ) {
        Row(modifier = Modifier.fillMaxSize().padding(5 .dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = title, fontStyle = FontStyle.Italic, fontSize = 25.sp)
                Text("(Today)", fontFamily = FontFamily.Serif, fontSize = 10.sp)
            }

              Icon(
                painter = painterResource(icon),
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary
            )


            Column(modifier = Modifier.padding(6.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = value, fontStyle = FontStyle.Italic, fontSize = 25.sp)
                Text(text = unit, fontFamily = FontFamily.Serif, fontSize = 10.sp)

            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
}
