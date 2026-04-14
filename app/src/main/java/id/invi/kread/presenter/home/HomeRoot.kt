package id.invi.kread.presenter.home

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import id.invi.kread.domain.checkChannelValue
import id.invi.kread.ui.components.melecule.LoadingDialog
import kotlinx.coroutines.launch

@Composable
fun HomeRoot(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onLogoutSuccess: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val homeState by viewModel.homeState.collectAsStateWithLifecycle()
    val deleteTrackingEvent = remember { viewModel.deleteTrackingEvent }
    val logoutEvent = remember { viewModel.logoutEvent }

    var isShowLoadingDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        deleteTrackingEvent.checkChannelValue(
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
                viewModel.getTrackings()
            },
        )
    }

    LaunchedEffect(Unit) {
        logoutEvent.checkChannelValue(
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
                onLogoutSuccess()
            },
        )
    }

    HomeScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        state = homeState,
        onRefresh = {
            viewModel.setPullToRefreshIndicator(true)
            viewModel.getTrackings()
        },
        onSaveTrackingSuccess = {
            viewModel.getTrackings()
        },
        onLogoutClick = {
            viewModel.logout()
        },
        onDeleteTrackingClick = {
            viewModel.deleteTracking(it)
        }
    )

    if (isShowLoadingDialog) {
        LoadingDialog()
    }
}
