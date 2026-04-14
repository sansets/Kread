package id.invi.kread.presenter.register

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
fun RegisterRoot(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = hiltViewModel(),
    onBackNavigateClick: () -> Unit,
    onRegisterSuccess: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val registerEvent = remember { viewModel.registerEvent }
    var isShowLoadingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        registerEvent.checkChannelValue(
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
                onRegisterSuccess()
            },
        )
    }

    RegisterScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        isShowLoadingDialog = isShowLoadingDialog,
        onBackNavigateClick = onBackNavigateClick,
        onRegisterClick = { email, password ->
            viewModel.register(
                email = email,
                password = password,
            )
        },
    )
}
