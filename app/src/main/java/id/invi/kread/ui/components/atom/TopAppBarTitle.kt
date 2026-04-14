package id.invi.kread.ui.components.atom

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import id.invi.kread.R
import id.invi.kread.ui.theme.KreadTheme

@Composable
fun TopAppBarTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    Text(
        modifier = modifier,
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
    )
}

@Preview
@Composable
fun TopAppBarTitlePreview() {
    KreadTheme {
        TopAppBarTitle(
            title = stringResource(R.string.app_name),
        )
    }
}