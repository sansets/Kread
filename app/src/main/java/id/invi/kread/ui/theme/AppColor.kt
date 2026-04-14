package id.invi.kread.ui.theme

import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LightColors = lightColorScheme(
    primary = Color(0xFF54B87C),
    primaryContainer = Color(0xFF255237),
    background = Color(0xFFFFFFFF),
    surface = Color(0xFFFFFFFF)
)

class ExtendedColors(
    val textPrimary: Color = Color.Unspecified,
    val textSecondary: Color = Color.Unspecified,
    val textHint: Color = Color.Unspecified,
    val icon: Color = Color.Unspecified,
    val backgroundBottomSheet: Color = Color.Unspecified,
    val backgroundCard: Color = Color.Unspecified,
    val divider: Color = Color.Unspecified,
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors()
}

val LightExtendedColors = ExtendedColors(
    textPrimary = Color(0xFF212121),
    textSecondary = Color(0xFF757575),
    textHint = Color(0xFF757575),
    icon = Color(0xFF212121),
    backgroundBottomSheet = Color(0xFFFFFFFF),
    backgroundCard = Color(0xFFF5F5F5),
    divider = Color(0xFFE0E0E0),
)