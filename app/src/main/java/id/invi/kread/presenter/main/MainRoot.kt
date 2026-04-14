package id.invi.kread.presenter.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MainRoot(
    modifier: Modifier = Modifier,
    onLogoutSuccess: () -> Unit,
) {
    MainScreen(
        modifier = modifier,
        onLogoutSuccess = onLogoutSuccess,
    )
}