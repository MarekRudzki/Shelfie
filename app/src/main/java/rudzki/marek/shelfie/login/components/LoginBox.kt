package rudzki.marek.shelfie.login.components

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import rudzki.marek.shelfie.login.viewModel.LoginViewModel
import rudzki.marek.shelfie.login.viewModel.UiEvent
import rudzki.marek.shelfie.shared.TopBarWithBackButton

@Composable
fun LoginBox(
    onLoginBoxChanged: (Int) -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.LoginError -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                else -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(25.dp))
            .height((LocalConfiguration.current.screenHeightDp.dp / 2.08f))
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            TopBarWithBackButton(
                title = "Login",
                showIcon = false,
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "Please type your phone number to login or register."
            )
            Spacer(modifier = Modifier.height(8.dp))
            PhoneNumberInput(
                isPhoneNumberValid = viewModel.isPhoneNumberValid,
                onPhoneNumberValidation = { viewModel.updatePhoneNumberValid(it)} ,
                onPhoneNumberChange = { viewModel.updatePhoneNumber(it)}
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .align(Alignment.CenterHorizontally),
                text = "We will send a SMS to verify your account."
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(16.dp),
                onClick = {
                    viewModel.startVerification(activity) {
                        onLoginBoxChanged(1)
                    }
                },
                enabled = viewModel.isPhoneNumberValid && !viewModel.isLoading,
                colors = ButtonDefaults.buttonColors(
                ),
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Confirm",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
