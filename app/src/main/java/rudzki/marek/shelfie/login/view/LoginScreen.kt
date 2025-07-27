package rudzki.marek.shelfie.login.view


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import rudzki.marek.shelfie.login.view.components.ImageWithSlogan
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import rudzki.marek.shelfie.login.view.components.LoginBox
import rudzki.marek.shelfie.login.view.components.VerifyBox


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    ) {
    var loginBoxDisplayed by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(WindowInsets.ime.asPaddingValues())
    ) {
        ImageWithSlogan()
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimatedContent(
                targetState = loginBoxDisplayed,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInHorizontally { width -> width } with slideOutHorizontally { width -> -width }
                    } else {
                        slideInHorizontally { width -> -width } with slideOutHorizontally { width -> width }
                    }
                },
                label = "LoginBoxSwitcher"
            ) { targetState ->
                when (targetState) {
                    0 -> LoginBox(
                        onLoginBoxChanged = { loginBoxDisplayed = it }
                    )

                    1 -> VerifyBox(
                        onLoginBoxChanged = { loginBoxDisplayed = it },
                        onLoginSuccess = { onLoginSuccess() }
                    )
                }
            }
        }
    }
}
