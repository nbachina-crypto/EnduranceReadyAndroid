package com.narenbachina.enduranceready.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.narenbachina.enduranceready.R
import com.narenbachina.enduranceready.data.HealthConnectManager
import com.narenbachina.enduranceready.data.HealthRepositoryImplementation
import com.narenbachina.enduranceready.navigation.NavigationDestination
import com.narenbachina.enduranceready.viewmodels.ReadinessViewModel
import com.narenbachina.enduranceready.viewmodels.ReadinessViewModelFactory

//Main Composable for Readiness Score Screen
@Composable
fun ReadinessScoreScreen(navController: NavController,
                         viewModel: ReadinessViewModel
){
val readinessUiState by viewModel.uiState.collectAsState()
    val sleepValue=readinessUiState.sleepHours?.let {
        String.format("%.1f hrs",it)
    }?:"--"

    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 8.dp)

    ) {
        item {
            DaySelectorSection(currentDayLabel = "Today", onPreviousClick = {}, onNextClick = {})

        }
        item {
            if(readinessUiState.isLoading){
                Text(text = "Loading...")
            }else if(readinessUiState.error!=null) {
                Text(text = "Error: ${readinessUiState.error}")
            }
            else{
                ScoreSection(readinessScore = readinessUiState.sleepHours?.toString()?:"--",
                    readinessScoreCategory = if(readinessUiState.restingHeartRate!=null)"Loaded" else "Loading"
                )
            }

        }
        item {
            DetailedReportReadinesScore(
                sleepValue=sleepValue,
                nutritionValue=2000.toString(),
                heartRateValue=2000.toString(),
                movementValue=2000.toString(),

            )

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
fun ScoreSection(readinessScore: String, readinessScoreCategory: String){

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
fun DetailedReportReadinesScore(
    sleepValue: String,
    nutritionValue: String,
    heartRateValue: String,
    movementValue: String,
){
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

            // Sleep (Top rounded)
            ReadinessDetailCard(
                iconRes = R.drawable.sleepicon,
                message = "Sleep",
                value = sleepValue,
                contentDescription = "Sleep Parameter",
                topStart = 16.dp,
                topEnd = 16.dp,

            )

            // Nutrition (Middle - sharp)
            ReadinessDetailCard(
                iconRes = R.drawable.foodicon,
                message = "Nutrition",
                value = nutritionValue,
                contentDescription = "Nutrition Parameter",

            )

            // Resting Heart Rate (Middle - sharp)
            ReadinessDetailCard(
                iconRes = R.drawable.rhr,
                message = "Resting Heart Rate",
                value = heartRateValue,
                contentDescription = "Resting Heart Rate Parameter",

            )

            // Movement (Bottom rounded)
            ReadinessDetailCard(
                iconRes = R.drawable.exerciseicon,
                message = "Movement",
                value = movementValue,
                contentDescription = "Movement Parameter",
                bottomStart = 16.dp,
                bottomEnd = 16.dp,

            )
        }


    }
}


@Composable

fun ReadinessDetailCard(
    iconRes: Int,
    message: String,
    value: String,
    contentDescription: String,
    topStart: Dp = 0.dp,
    topEnd: Dp = 0.dp,
    bottomStart: Dp = 0.dp,
    bottomEnd: Dp = 0.dp,
){
    ElevatedCard(modifier = Modifier.fillMaxWidth().height(75.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),

        shape = RoundedCornerShape(
            topStart = topStart, // Rounded corner
            topEnd = topEnd,   // Rounded corner
            bottomStart = bottomStart, // Square corner
            bottomEnd = bottomEnd    // Square corner
        )
    ){
        Row(
            modifier = Modifier.fillMaxSize().padding(3.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(iconRes),
                contentDescription = contentDescription,
                tint = MaterialTheme.colorScheme.primary,
            )

            Text(text = message)
            Text(text = value)

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
            , border = BorderStroke(width = 3.dp, color = Color.Gray)
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
    ReadinessScoreScreen(navController = rememberNavController(),
    viewModel  = viewModel(factory = ReadinessViewModelFactory(
        repository = HealthRepositoryImplementation(
            HealthConnectManager(LocalContext.current)
        ),
        healthConnectManager = HealthConnectManager(LocalContext.current)
    )
    )
    )
}