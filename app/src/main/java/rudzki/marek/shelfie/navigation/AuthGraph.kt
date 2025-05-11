package rudzki.marek.shelfie.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import rudzki.marek.shelfie.login.screens.LoginScreen

fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation(startDestination = "login", route = "auth") {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("main") {
                        popUpTo("auth") { inclusive = true }
                    }
                }
            )
        }
    }
}