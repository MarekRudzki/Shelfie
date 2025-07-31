package rudzki.marek.shelfie.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    secondary = secondaryLight,
    error = errorLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
)

@Composable
fun ShelfieTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightScheme,
        typography = AppTypography,
        content = content
    )
}

