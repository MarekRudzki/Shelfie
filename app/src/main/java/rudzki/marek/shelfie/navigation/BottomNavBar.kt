package rudzki.marek.shelfie.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val bottomInset = WindowInsets.systemBars.getBottom(LocalDensity.current)
    val currentRoute = navController.currentDestination?.route

    val items = listOf(
        BottomNavItem("homeScreen", Icons.Default.Home, "Home"),
        BottomNavItem("searchScreen", Icons.Default.Search, "Search"),
        BottomNavItem("profileScreen", Icons.Default.Person, "Profile")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = bottomInset.dp / 2)
            .height(70.dp)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
                items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(25.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.White,
                    selectedIconColor =  MaterialTheme.colorScheme.primary
                ),

            )
        }
    }
}