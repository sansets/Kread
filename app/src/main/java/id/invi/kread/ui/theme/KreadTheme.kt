package id.invi.kread.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

object KreadTheme {
    val colorScheme: ColorScheme
        @Composable
        get() = LightColors

    val shapes: Shapes
        @Composable
        get() = MaterialTheme.shapes

    val typography: Typography
        @Composable
        get() = kreadTypography()

    val extendedColors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current
}

@Composable
fun KreadTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalExtendedColors provides LightExtendedColors) {
        MaterialTheme(
            colorScheme = KreadTheme.colorScheme,
            shapes = KreadTheme.shapes,
            typography = KreadTheme.typography,
            content = content
        )
    }
}
