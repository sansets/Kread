package id.invi.kread.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import id.invi.kread.R

@Composable
fun figtreeFontFamily() = FontFamily(
    Font(R.font.figtree_light, FontWeight.Light),
    Font(R.font.figtree_regular, FontWeight.Normal),
    Font(R.font.figtree_medium, FontWeight.Medium),
    Font(R.font.figtree_semibold, FontWeight.SemiBold),
    Font(R.font.figtree_bold, FontWeight.Bold),
    Font(R.font.figtree_extrabold, FontWeight.ExtraBold),
    Font(R.font.figtree_black, FontWeight.Black),
)

@Composable
fun kreadTypography(): Typography {
    val figtree = figtreeFontFamily()
    val lineHeightStyle = LineHeightStyle(
        alignment = LineHeightStyle.Alignment.Center,
        trim = LineHeightStyle.Trim.Both
    )

    return Typography(
        displayLarge = Typography().displayLarge.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        displayMedium = Typography().displayMedium.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        displaySmall = Typography().displaySmall.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        headlineLarge = Typography().headlineLarge.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        headlineMedium = Typography().headlineMedium.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        headlineSmall = Typography().headlineSmall.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        titleLarge = Typography().titleLarge.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        titleMedium = Typography().titleMedium.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        titleSmall = Typography().titleSmall.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        bodyLarge = Typography().bodyLarge.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        bodyMedium = Typography().bodyMedium.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        bodySmall = Typography().bodySmall.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        labelLarge = Typography().labelLarge.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        labelMedium = Typography().labelMedium.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
        labelSmall = Typography().labelSmall.copy(
            fontFamily = figtree,
            lineHeightStyle = lineHeightStyle
        ),
    )
}
