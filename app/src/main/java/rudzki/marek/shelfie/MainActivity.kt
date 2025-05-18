package rudzki.marek.shelfie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import rudzki.marek.shelfie.navigation.authGraph
import rudzki.marek.shelfie.navigation.mainGraph
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
                    startDestination = "auth"
                ) {
                    authGraph(navController)
                    mainGraph(navController)
                }
            }
        }
    }
}
