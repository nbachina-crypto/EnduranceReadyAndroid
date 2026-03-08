package com.narenbachina.enduranceready.screens

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.health.connect.client.PermissionController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.narenbachina.enduranceready.data.HealthConnectManager
import com.narenbachina.enduranceready.viewmodels.ReadinessViewModel
import kotlin.contracts.contract

@Composable
fun ProfileScreen(
    viewModel: ReadinessViewModel,
    healthConnectManager: HealthConnectManager
){

    LaunchedEffect(Unit) {
        viewModel.checkPermissionsAndLoad()
    }



    val uiState by viewModel.uiState.collectAsState()
    val hasPermissions by viewModel.hasPermissions.collectAsState()

    val permissionsLauncher = rememberLauncherForActivityResult(
        PermissionController.createRequestPermissionResultContract()
    ){granted->

        if(granted.containsAll(healthConnectManager.permissions)){
            viewModel.checkPermissionsAndLoad()

        }


    }

    if(!hasPermissions){
        AskPermissions(
            onGrantCick = {
                Log.d("permission", "Button Clicked")
                permissionsLauncher.launch(
                    healthConnectManager.permissions
                )
            }
        )
        return
    }

    WeightDisplay(
        heading = "Your Weight",
        weight = uiState.userWeight.toString()
    )



}

@Composable
fun AskPermissions(onGrantCick: () -> Unit){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Health Connect permissions are needed to show your readiness score.")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            onGrantCick()
        }) {
            Text("Grant Permissions")
        }
    }
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
