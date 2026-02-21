package com.narenbachina.enduranceready.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.narenbachina.enduranceready.R
import com.narenbachina.enduranceready.navigation.NavigationDestination
import com.narenbachina.enduranceready.navigation.TopNavigationBar

//Main Composable for Readiness Score Screen
@Composable
fun ReadinessScoreScreen(navController: NavController){

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 8.dp)

    ) {
        item {
            DaySelectorSection(currentDayLabel = "Today", onPreviousClick = {}, onNextClick = {})

        }
        item {
            ScoreSection(readinessScore = "90", readinessScoreCategory = "High")

        }
        item {
            DetailedReportReadinesScore(onSleepCardClick = {
                navController.navigate(NavigationDestination.SleepDetails.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }

                    launchSingleTop=true

                    restoreState=true
                }

            }, onNutritionCardClick = {
                navController.navigate(NavigationDestination.FoodDetails.route){
                    popUpTo(navController.graph.startDestinationId){
                        saveState=true
                    }

                    launchSingleTop=true

                    restoreState=true
                }
            }, onMovementCardClick = {
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
            ReadinessExplanationSection()
        }
    }

}

//Day selector section composable
@Composable
fun DaySelectorSection(currentDayLabel: String,onPreviousClick:()->Unit,onNextClick:()->Unit){
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Movement Parameter"
            )
        }

        Text(text = currentDayLabel)

        IconButton(onClick = onNextClick) {
            Icon(
                Icons.Default.ArrowForward,
                contentDescription = "Movement Parameter"
            )
        }


    }
}


//Score section composable
@Composable
fun ScoreSection(readinessScore: String,readinessScoreCategory: String){
    Card(
        modifier = Modifier.fillMaxWidth().wrapContentHeight()
            .padding(4.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){

            Column(

            ) {
                Text(text = readinessScore, fontSize = 30.sp)
                Text(text = readinessScoreCategory)
            }


            Icon(
                painter = painterResource(R.drawable.readinessscore),
                contentDescription = "Readiness Score",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(50.dp)
            )

        }

    }
}


//Detail report readiness score composable
@Composable
fun DetailedReportReadinesScore(onSleepCardClick:()-> Unit,onNutritionCardClick:()-> Unit,onMovementCardClick:()-> Unit){
    Card(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(3.dp)

    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(text = "What impacted your readiness score?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(text = "\n"+"Fitbit looks for key indicators of recovery to give you a readiness score each day "+"\n")

            ElevatedCard(modifier = Modifier.fillMaxWidth().height(75.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),

                shape = RoundedCornerShape(
                    topStart = 16.dp, // Rounded corner
                    topEnd = 16.dp,   // Rounded corner
                    bottomStart = 0.dp, // Square corner
                    bottomEnd = 0.dp    // Square corner
                ),onClick = onSleepCardClick
            ){
                Row(
                    modifier = Modifier.fillMaxSize().padding(3.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.sleepicon),
                        contentDescription = "Sleep Parameter",
                        tint = MaterialTheme.colorScheme.primary,
                    )

                    Text(text = "Sleep about usual")
                }
            }

            ElevatedCard(modifier = Modifier.fillMaxWidth().height(75.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                shape = RoundedCornerShape(
                    topStart = 0.dp, // Rounded corner
                    topEnd = 0.dp,   // Rounded corner
                    bottomStart = 0.dp, // Square corner
                    bottomEnd = 0.dp    // Square corner
                ), onClick = onNutritionCardClick
            ){
                Row(
                    modifier = Modifier.fillMaxSize().padding(3.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.foodicon),
                        contentDescription = "Food Parameter",
                        tint = MaterialTheme.colorScheme.primary,
                    )

                    Text(text = "nutriton on point")
                }
            }

            ElevatedCard(modifier = Modifier.fillMaxWidth().height(75.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),

                shape = RoundedCornerShape(
                    topStart = 0.dp, // Rounded corner
                    topEnd = 0.dp,   // Rounded corner
                    bottomStart = 16.dp, // Square corner
                    bottomEnd = 16.dp    // Square corner
                ), onClick = onMovementCardClick
            ){
                Row(
                    modifier = Modifier.fillMaxSize().padding(3.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.exerciseicon),
                        contentDescription = "Movement Parameter",
                        tint = MaterialTheme.colorScheme.primary,
                    )

                    Text(text = "Movement on track")
                }
            }
        }


    }
}


//Readiness explanation section composable
@Composable
fun ReadinessExplanationSection(){
    Card(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
            .padding(3.dp)
            , border = BorderStroke(width = 1.dp, color = Color.Gray)
    ) {

        Column(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "About Daily Readiness",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp
            )

            Text(
                buildAnnotatedString {
                    append("\n"+"We calculate a daily readiness score to help you understand how ready your body is for a workout. Your score compares your sleep, nutriton, and movement  against your personal baseline.\n" +
                            "\n" +
                            "You need to wear your device continuously for accurate results"+"\n"+"\n"
                    )

                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append("Making the Most of Readiness"+"\n")
                    }

                    append("Your score comes with a rating of low, moderate, or high. You can use these to plan your daily activity or wellness routine.\n" +
                            "Here's what your readiness indicates:\n" +
                            "\n" +
                            "- Low (1-29): Prioritize recovery with lower intensity exercises like stretching and yoga.\n"+"\n" +
                            "- Moderate (30-64): Your heart rate and recent sleep are about usual for you, and your body is balancing exercise and stress with recovery.\n"+"\n" +
                            "- High (65-100): Your body is well-rested and recovered."+"\n"+"\n"
                    )
                    append("Regardless of your readiness score, always be aware of your personal limits when exercising to " +
                            "avoid injury or overtraining. Always consult your physician if you have questions related to an exercise program or health issues.  "
                            )
                }

            )

        }


    }
}


//Preview
@Preview(showBackground = true)
@Composable
fun ReadinessScoreScreenPreview(){

}