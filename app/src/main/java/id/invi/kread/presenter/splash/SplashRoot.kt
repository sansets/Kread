package id.invi.kread.presenter.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import id.invi.kread.domain.checkChannelValue

@Composable
fun SplashRoot(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    onAuthenticationCheck: (isLoggedIn : Boolean) -> Unit,
) {
    val checkIsLoggedInEvent = remember { viewModel.checkIsLoggedInEvent }

    LaunchedEffect(Unit) {
        checkIsLoggedInEvent.checkChannelValue(
            onError = {
                onAuthenticationCheck(false)
            },
            onSuccess = {
                onAuthenticationCheck(it)
            },
        )
    }

    SplashScreen(
        modifier = modifier,
    )
}