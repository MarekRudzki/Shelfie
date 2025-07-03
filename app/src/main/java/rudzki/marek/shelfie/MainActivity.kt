package rudzki.marek.shelfie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import rudzki.marek.shelfie.home.view.BookDetailsScreen
import rudzki.marek.shelfie.home.view.HomeScreen
import rudzki.marek.shelfie.home.view.SearchResultScreen
import rudzki.marek.shelfie.login.view.LoginScreen
import rudzki.marek.shelfie.ui.theme.ShelfieTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShelfieTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("login") {
                        LoginScreen(
                            onLoginSuccess = {
                                navController.navigate("home") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    }

                    composable("home") {
                        HomeScreen(
                            onNavigateToLogin = {
                                navController.navigate("login") {
                                    popUpTo("auth") { inclusive = true }
                                }
                            },
                            navController = navController,
                        )
                    }

                    composable(
                        route = "search_result?query={query}&genre={genre}",
                        arguments = listOf(
                            navArgument("query") {
                                type = NavType.StringType
                                defaultValue = ""
                                nullable = true
                            },
                            navArgument("genre") {
                                type = NavType.StringType
                                defaultValue = ""
                                nullable = true
                            }
                        )
                    ) { backStackEntry ->
                        val query = backStackEntry.arguments?.getString("query") ?: ""
                        val genre = backStackEntry.arguments?.getString("genre") ?: ""

                        SearchResultScreen(
                            query = query,
                            genre = genre
                        )
                    }

                    composable("book_details") {
                        BookDetailsScreen()
                    }
                }
            }
        }
    }
}
