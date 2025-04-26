package rudzki.marek.shelfie.login.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import rudzki.marek.shelfie.R

@Composable
fun ImageWithSlogan() {
    Box(
        modifier = Modifier
            .height(LocalConfiguration.current.screenHeightDp.dp / 2)
            .width(LocalConfiguration.current.screenWidthDp.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_logo),
            contentDescription = "App logo image",
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(
                    bottomEnd = 40.dp,
                    bottomStart = 40.dp,
                ))
                .background(color = MaterialTheme.colorScheme.primary),
            contentScale = ContentScale.FillBounds,
        )
        Box(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .align(Alignment.BottomCenter)
        ) {
            RotatingSlogan()
        }
    }
}