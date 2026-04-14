package id.invi.kread.presenter.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.invi.kread.presenter.home.HomeRoot

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onLogoutSuccess: () -> Unit,
) {
    HomeRoot(
        modifier = modifier,
        onLogoutSuccess = onLogoutSuccess,
    )
}
