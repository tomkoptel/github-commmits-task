package com.olderwold.jlabs.github

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainDestinations.HOME_ROUTE
) {
    val actions = remember(navController) { MainActions(navController) }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(MainDestinations.HOME_ROUTE) {
            HomeScreen(
                navigateToCommits = actions.navigateToCommits
            )
        }
        composable(MainDestinations.COMMITS_PATH) {  backStackEntry ->
            val repoName = backStackEntry.arguments
                ?.getString(MainDestinations.REPO_NAME_KEY)
                .let { requireNotNull(it) { "Can not navigate to details without repoName" } }
            CommitsScreen(
                repoName = repoName,
                onBack = actions.upPress
            )
        }
    }
}

/**
 * Destinations used in the the app.
 */
object MainDestinations {
    const val HOME_ROUTE = "home"
    const val COMMITS_ROUTE = "commits"
    const val REPO_NAME_KEY = "repoName"

    const val COMMITS_PATH = "$COMMITS_ROUTE/{$REPO_NAME_KEY}"
}

/**
 * Models the navigation actions in the app.
 */
class MainActions(navController: NavHostController) {
    val navigateToCommits: (String) -> Unit = { repoName: String ->
        navController.navigate("${MainDestinations.COMMITS_ROUTE}/$repoName")
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}
