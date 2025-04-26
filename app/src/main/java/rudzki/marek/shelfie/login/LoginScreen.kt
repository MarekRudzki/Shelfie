package rudzki.marek.shelfie.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import rudzki.marek.shelfie.login.widgets.ImageWithSlogan
import rudzki.marek.shelfie.login.widgets.LoginBox
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import rudzki.marek.shelfie.login.widgets.VerifyBox

@Composable
fun LoginScreen(innerPaddingValues: PaddingValues) {
    var loginBoxDisplayed by remember { mutableIntStateOf(0) }

    Column (
        modifier = Modifier
            .padding(bottom = innerPaddingValues.calculateBottomPadding())
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        ImageWithSlogan()
        if (loginBoxDisplayed == 0) LoginBox(//TODO animated visibility
            onLoginBoxChanged = { loginBoxDisplayed = it}
        )
        if (loginBoxDisplayed == 1) VerifyBox(//TODO animated visibility
            onLoginBoxChanged = { loginBoxDisplayed = it}
        )
    }
}







