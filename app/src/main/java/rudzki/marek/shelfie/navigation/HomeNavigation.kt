package rudzki.marek.shelfie.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import rudzki.marek.shelfie.account.screens.AccountScreen
import rudzki.marek.shelfie.home.screens.HomeScreen
import rudzki.marek.shelfie.search.screens.SearchScreen

@Composable
fun HomeNavigation(
    navController: NavHostController
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry.value?.destination?.route
    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            when (currentRoute) {
                "homeScreen" -> HomeScreen()
                "searchScreen" -> SearchScreen()
                "profileScreen" -> AccountScreen(
                    onNavigateToLogin = {
                        navController.navigate("login") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}