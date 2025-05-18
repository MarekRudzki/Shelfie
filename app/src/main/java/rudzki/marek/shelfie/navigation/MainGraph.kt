package rudzki.marek.shelfie.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainGraph(rootNavController: NavHostController) {
    navigation(startDestination = "homeScreen", route = "main") {
        composable("homeScreen") {
            HomeNavigation(rootNavController)
        }

        composable("searchScreen") {
            HomeNavigation(rootNavController)
        }

        composable("profileScreen") {
            HomeNavigation(rootNavController)
        }
    }
}

