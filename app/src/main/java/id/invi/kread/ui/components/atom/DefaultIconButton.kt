package id.invi.kread.ui.components.atom

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.invi.kread.R
import id.invi.kread.ui.theme.KreadTheme

@Composable
fun DefaultIconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentDescription: String? = null,
    @DrawableRes iconRes: Int,
    iconTint: Color = KreadTheme.extendedColors.icon,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = painterResource(iconRes),
            contentDescription = contentDescription,
            tint = iconTint,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultIconButtonPreview() {
    KreadTheme {
        DefaultIconButton(
            onClick = {},
            iconRes = R.drawable.rounded_close_24,
        )
    }
}