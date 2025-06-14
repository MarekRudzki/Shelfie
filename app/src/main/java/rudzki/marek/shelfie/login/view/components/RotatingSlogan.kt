package rudzki.marek.shelfie.login.view.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import rudzki.marek.shelfie.R

@Composable
fun RotatingSlogan() {
    val slogansList = listOf(
        "Read, Discover, Enjoy",
        "Endless Stories Await",
        "The World of Books",
        "Unlock Your Imagination",
        "Your Next Chapter Awaits",
        "Discover, Read, Repeat",
        "Book for Every Mood",
        "Where Stories Live",
        "A New Chapter Starts Now",
        "Next Chapter, Next Adventure",
        "Chapter One Starts Here",
        "Where Every Chapter Matters",
        "New Stories, New Beginnings",
        "The Next Chapter is Waiting"
    )

    var currentSlogan by remember { mutableStateOf(slogansList.random()) }
    var isVisible by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            isVisible = false
            delay(800)

            var newSlogan: String
            do {
                newSlogan = slogansList.random()
            } while (newSlogan == currentSlogan)
            currentSlogan = newSlogan
            delay(200)
            isVisible = true
        }
    }

    AnimatedVisibility(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        visible = isVisible,
        enter = fadeIn(tween(1200)),
        exit = fadeOut(tween(800))
    )
    {
        Text(
            text = currentSlogan,
            modifier = Modifier.padding(10.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(
                    googleFont = GoogleFont("Sriracha"),
                    fontProvider = GoogleFont.Provider(
                        providerAuthority = "com.google.android.gms.fonts",
                        providerPackage = "com.google.android.gms",
                        certificates = R.array.com_google_android_gms_fonts_certs
                    ),
                )
            ),
            fontWeight = FontWeight.W500,
            color = Color.White,
            fontSize = 22.sp,
        )
    }
}