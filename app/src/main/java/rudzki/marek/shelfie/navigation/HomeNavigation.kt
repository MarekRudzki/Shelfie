package rudzki.marek.shelfie.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import rudzki.marek.shelfie.home.screens.HomeScreen
import rudzki.marek.shelfie.profile.screens.ProfileScreen
import rudzki.marek.shelfie.search.screens.SearchScreen

@Composable
fun HomeNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry = navController.currentBackStackEntryAsState().value

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "homeScreen",
            modifier = androidx.compose.ui.Modifier.padding(innerPadding)
        ) {
            composable("homeScreen") { HomeScreen() }
            composable("searchScreen") { SearchScreen() }
            composable("profileScreen") { ProfileScreen() }
        }
    }
}