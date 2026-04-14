package id.invi.kread.presenter.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import id.invi.kread.R
import id.invi.kread.domain.checkResult
import id.invi.kread.domain.Result
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.domain.model.getDummyHabitTrackings
import id.invi.kread.presenter.addtracking.AddTrackingRoot
import id.invi.kread.presenter.home.components.HabitTrackingActionBottomSheet
import id.invi.kread.presenter.home.components.HomeMenuBottomSheet
import id.invi.kread.ui.components.atom.DefaultIconButton
import id.invi.kread.ui.components.atom.TopAppBarTitle
import id.invi.kread.ui.components.organism.HabitTrackingCard
import id.invi.kread.ui.theme.KreadTheme
import id.invi.kread.util.formatToDisplay
import kotlin.collections.map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    state: HomeState,
    onRefresh: () -> Unit,
    onSaveTrackingSuccess: () -> Unit,
    onDeleteTrackingClick: (id: String) -> Unit,
    onSyncClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    var isMenuBottomSheetShown by remember { mutableStateOf(false) }
    var isAddTrackingBottomSheetShown by remember { mutableStateOf(false) }
    var selectedHabitTracking by remember { mutableStateOf<HabitTracking?>(null) }
    var selectedUpdateHabitTracking by remember { mutableStateOf<HabitTracking?>(null) }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                modifier = Modifier.heightIn(56.dp),
                navigationIcon = {
                    DefaultIconButton(
                        iconRes = R.drawable.rounded_menu_24,
                        iconTint = KreadTheme.colorScheme.primaryContainer,
                        onClick = {
                            isMenuBottomSheetShown = true
                        },
                    )
                },
                title = {
                    TopAppBarTitle(
                        title = stringResource(R.string.app_name),
                    )
                },
                actions = {
                    DefaultIconButton(
                        iconRes = R.drawable.rounded_sync_24,
                        iconTint = KreadTheme.colorScheme.primaryContainer,
                        onClick = onSyncClick,
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    isAddTrackingBottomSheetShown = true
                },
                containerColor = KreadTheme.colorScheme.primary,
                contentColor = KreadTheme.colorScheme.onPrimary,
            ) {
                Icon(
                    painter = painterResource(R.drawable.rounded_add_24),
                    contentDescription = null,
                )
            }
        },
    ) { innerPadding ->
        PullToRefreshBox(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            isRefreshing = state.isShowPullToRefreshIndicator,
            onRefresh = onRefresh,
        ) {
            state.habitTrackings.checkResult(
                onError = {
                    HomeErrorState()
                },
                onLoading = {
                    HomeLoadingState()
                },
                onSuccess = {
                    if (it.isNotEmpty()) {
                        HomeSuccessState(
                            habitTrackings = it,
                            onHabitTrackingItemClick = { habitTracking ->
                                selectedHabitTracking = habitTracking
                            },
                            onHabitTrackingSyncClick = {
                                onSyncClick()
                            }
                        )
                    } else {
                        HomeEmptyState()
                    }
                }
            )
        }
    }

    if (isMenuBottomSheetShown) {
        HomeMenuBottomSheet(
            onDismiss = {
                isMenuBottomSheetShown = false
            },
            onLogoutClick = onLogoutClick,
        )
    }

    if (selectedHabitTracking != null) {
        selectedHabitTracking?.let {
            HabitTrackingActionBottomSheet(
                onDismiss = {
                    selectedHabitTracking = null
                },
                habitTracking = it,
                onDeleteClick = {
                    onDeleteTrackingClick(it.id)
                    selectedHabitTracking = null
                },
                onEditClick = {
                    selectedUpdateHabitTracking = it
                    selectedHabitTracking = null
                }
            )
        }
    }

    if (selectedUpdateHabitTracking != null) {
        selectedUpdateHabitTracking?.let {
            AddTrackingRoot(
                habitTracking = it,
                onDismiss = {
                    selectedUpdateHabitTracking = null
                },
                onAddTrackingSuccess = {
                    selectedUpdateHabitTracking = null
                    onSaveTrackingSuccess()
                }
            )
        }
    }

    if (isAddTrackingBottomSheetShown) {
        AddTrackingRoot(
            onDismiss = {
                isAddTrackingBottomSheetShown = false
            },
            onAddTrackingSuccess = {
                isAddTrackingBottomSheetShown = false
                onSaveTrackingSuccess()
            }
        )
    }
}

@Composable
fun HomeLoadingState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun HomeErrorState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.message_failed_to_load_data),
        )
    }
}

@Composable
fun HomeEmptyState(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = stringResource(R.string.message_empty_tracking_history),
        )
    }
}

@Composable
fun HomeSuccessState(
    modifier: Modifier = Modifier,
    habitTrackings: List<Pair<HabitTracking, Result<Unit>>>,
    onHabitTrackingItemClick: (HabitTracking) -> Unit,
    onHabitTrackingSyncClick: (HabitTracking) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(items = habitTrackings) {
            HabitTrackingCard(
                bookTitle = it.first.bookTitle,
                readingDate = it.first.readingDate.formatToDisplay(),
                readingStartTime = it.first.readingStartTime,
                readingEndTime = it.first.readingEndTime,
                syncStatus = it.second,
                onClick = {
                    onHabitTrackingItemClick(it.first)
                },
                onSyncClick = {
                    onHabitTrackingSyncClick(it.first)
                }
            )
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    KreadTheme {
        HomeScreen(
            snackbarHostState = SnackbarHostState(),
            state = HomeState(
                habitTrackings = Result.Success(
                    getDummyHabitTrackings().map {
                        val syncStatus =
                            if (it.isSynchronized) Result.Success(Unit)
                            else Result.Error(Exception(""))
                        Pair(it, syncStatus)
                    }
                )
            ),
            onRefresh = {},
            onSyncClick = {},
            onSaveTrackingSuccess = {},
            onDeleteTrackingClick = {},
            onLogoutClick = {},
        )
    }
}