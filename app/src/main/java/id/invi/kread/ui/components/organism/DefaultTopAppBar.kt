package id.invi.kread.ui.components.organism

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import id.invi.kread.R
import id.invi.kread.ui.components.atom.TopAppBarTitle
import id.invi.kread.ui.theme.KreadTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    isShowBackNavigationIcon: Boolean = false,
    onBackNavigationClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = modifier,
        title = {
            if (!title.isNullOrEmpty()) {
                TopAppBarTitle(
                    title = title,
                )
            }
        },
        navigationIcon = {
            if (isShowBackNavigationIcon) {
                IconButton(
                    onClick = onBackNavigationClick,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.rounded_arrow_back_24),
                        contentDescription = null,
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun DefaultTopAppBarPreview() {
    KreadTheme {
        DefaultTopAppBar(
            onBackNavigationClick = {},
        )
    }
}
