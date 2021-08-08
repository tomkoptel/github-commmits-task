package com.olderwold.jlabs.github

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.olderwold.jlabs.github.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        ProvideWindowInsets {
            val navController = rememberNavController()
            val scaffoldState = rememberScaffoldState()

            Scaffold(
                scaffoldState = scaffoldState,
            ) {
                AppNavGraph(
                    navController = navController,
                )
            }
        }
    }
}
