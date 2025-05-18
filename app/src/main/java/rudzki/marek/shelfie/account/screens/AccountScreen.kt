package rudzki.marek.shelfie.account.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import rudzki.marek.shelfie.account.viewModel.AuthViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import rudzki.marek.shelfie.account.viewModel.AuthUiState

@Composable
fun AccountScreen(
    viewModel: AuthViewModel = viewModel(),
    onNavigateToLogin: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

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
            viewModel.resetToIdle()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Profile",
                style = androidx.compose.ui.text.TextStyle(
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.primary
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.logout()
                }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Log out",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600
                    )
                )
            }
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    viewModel.deleteAccount()
                }
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    text = "Delete account",
                    style = androidx.compose.ui.text.TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W600
                    )
                )
            }
        }
    }
}