package id.invi.kread.presenter.login

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import id.invi.kread.domain.checkChannelValue
import kotlinx.coroutines.launch

@Composable
fun LoginRoot(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = hiltViewModel(),
    onRegisterClick: () -> Unit,
    onLoginSuccess: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val loginEvent = remember { viewModel.loginEvent }
    var isShowLoadingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        loginEvent.checkChannelValue(
            onLoading = {
                isShowLoadingDialog = true
            },
            onError = {
                isShowLoadingDialog = false
                scope.launch {
                    snackbarHostState.showSnackbar(it?.message.orEmpty())
                }
            },
            onSuccess = {
                isShowLoadingDialog = false
                onLoginSuccess()
            },
        )
    }

    LoginScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        isShowLoadingDialog = isShowLoadingDialog,
        onLoginClick = { email, password ->
            viewModel.login(
                email = email,
                password = password,
            )
        },
        onRegisterClick = onRegisterClick,
    )
}
