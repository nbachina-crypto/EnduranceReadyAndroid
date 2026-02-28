package com.narenbachina.enduranceready.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.narenbachina.enduranceready.data.HealthConnectManager
import com.narenbachina.enduranceready.viewmodels.ReadinessViewModel

@Composable
fun ProfileScreen(
    viewModel: ReadinessViewModel
){

    val uiState by viewModel.uiState.collectAsState()

    val weight=uiState.userWeight?:0.0

    WeightDisplay(heading = "Weight", weight = "$weight kg")
}

@Composable
fun WeightDisplay(heading: String, weight: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = heading)
        Text(text = weight)
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
//    ProfileScreen()
}
