package rudzki.marek.shelfie.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation

fun NavGraphBuilder.mainGraph(rootNavController: NavHostController) {
    navigation(startDestination = "home", route = "main") {
        composable("home") {
            HomeNavigation()
        }
    }
}

