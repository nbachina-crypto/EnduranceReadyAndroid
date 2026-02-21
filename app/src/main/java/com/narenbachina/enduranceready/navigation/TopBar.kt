package com.narenbachina.enduranceready.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.narenbachina.enduranceready.screens.ReadinessScoreScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(presentScreenHeading: String,onBackClick:()-> Unit){

    CenterAlignedTopAppBar(
     title = {Text(text = presentScreenHeading)}  ,
        navigationIcon = {
            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "ArrowBack"
                )
            }
        }
    )

}

