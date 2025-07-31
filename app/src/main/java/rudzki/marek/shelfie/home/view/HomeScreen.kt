package rudzki.marek.shelfie.home.view

import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import rudzki.marek.shelfie.home.view.components.CategoriesGrid
import rudzki.marek.shelfie.home.view.components.HomeAppBar
import rudzki.marek.shelfie.home.view.components.SearchBox
import rudzki.marek.shelfie.home.viewModel.AuthUiState
import rudzki.marek.shelfie.home.viewModel.AuthViewModel

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController,
    onNavigateToLogin: () -> Unit,
) {
    val uiState by authViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val focusManager =  LocalFocusManager.current

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Deleted || uiState is AuthUiState.LoggedOut) {
            onNavigateToLogin()
        }
        if (uiState is AuthUiState.Error) {
            Toast.makeText(
                context,
                (uiState as AuthUiState.Error).message,
                Toast.LENGTH_LONG
            ).show()
            authViewModel.resetToIdle()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(WindowInsets.systemBars.asPaddingValues())
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                focusManager.clearFocus()
            }
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 8.dp,
                    end = 8.dp
                ),
            horizontalAlignment = Alignment.End
        ) {
            HomeAppBar(authViewModel)

            SearchBox(
                onSearch = { it ->
                   if (it != "") {
                       navController.navigate("search_result?query=${Uri.encode(it)}") {
                           popUpTo("home")
                       }
                   }
                }
            )

            CategoriesGrid(
                onCategorySelected = { it ->
                    navController.navigate("search_result?query=&genre=${Uri.encode(it)}") {
                        popUpTo("home")
                    }
                }
            )
        }
    }
}


