package rudzki.marek.shelfie.login.screens


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import rudzki.marek.shelfie.login.components.ImageWithSlogan
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import rudzki.marek.shelfie.login.components.LoginBox
import rudzki.marek.shelfie.login.components.VerifyBox


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun LoginScreen(innerPaddingValues: PaddingValues) {
    var loginBoxDisplayed by remember { mutableIntStateOf(1) }

    Column (
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        ImageWithSlogan()
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
                    onLoginBoxChanged = { loginBoxDisplayed = it }
                )
            }
        }
    }
}
