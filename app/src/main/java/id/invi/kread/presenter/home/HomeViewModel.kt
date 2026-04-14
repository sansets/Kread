package id.invi.kread.presenter.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.invi.kread.domain.AppRepository
import id.invi.kread.domain.EventResult
import id.invi.kread.domain.Result
import id.invi.kread.domain.checkResultNonComposable
import id.invi.kread.domain.sendError
import id.invi.kread.domain.sendLoading
import id.invi.kread.domain.sendSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeState())
    val homeState: StateFlow<HomeState> = _homeState.asStateFlow()

    private val _deleteTrackingEvent = Channel<EventResult<Unit>>()
    val deleteTrackingEvent = _deleteTrackingEvent

    private val _logoutEvent = Channel<EventResult<Unit>>()
    val logoutEvent = _logoutEvent

    init {
        getTrackings()
    }

    fun getTrackings() {
        viewModelScope.launch {
            appRepository.getTrackings()
                .collectLatest { result ->
                    result.checkResultNonComposable(
                        onLoading = {
                            if (_homeState.value.habitTrackings is Result.Default) {
                                _homeState.update { state ->
                                    state.copy(
                                        habitTrackings = Result.Loading,
                                    )
                                }
                            }
                        },
                        onError = { ex ->
                            _homeState.update { state ->
                                state.copy(
                                    isShowPullToRefreshIndicator = false,
                                    habitTrackings = Result.Error(ex),
                                )
                            }
                        },
                        onSuccess = {
                            _homeState.update { state ->
                                state.copy(
                                    isShowPullToRefreshIndicator = false,
                                    habitTrackings = Result.Success(
                                        it.map { habitTracking ->
                                            val syncStatus =
                                                if (habitTracking.isSynchronized) Result.Success(Unit)
                                                else Result.Error(Exception(""))
                                            Pair(habitTracking, syncStatus)
                                        }
                                    ),
                                )
                            }
                        }
                    )
                }
        }
    }

    fun setPullToRefreshIndicator(isShow: Boolean) {
        _homeState.update { state ->
            state.copy(
                isShowPullToRefreshIndicator = isShow
            )
        }
    }

    fun deleteTracking(id: String) {
        viewModelScope.launch {
            appRepository.deleteTracking(id)
                .collectLatest { result ->
                    result.checkResultNonComposable(
                        onLoading = {
                            _deleteTrackingEvent.sendLoading()
                        },
                        onError = { ex ->
                            _deleteTrackingEvent.sendError(ex)
                        },
                        onSuccess = {
                            _deleteTrackingEvent.sendSuccess(it)
                        }
                    )
                }
        }
    }

    fun logout() {
        viewModelScope.launch {
            appRepository.logout()
                .collectLatest { result ->
                    result.checkResultNonComposable(
                        onLoading = {
                            _logoutEvent.sendLoading()
                        },
                        onError = { ex ->
                            _logoutEvent.sendError(ex)
                        },
                        onSuccess = {
                            _logoutEvent.sendSuccess(it)
                        }
                    )
                }
        }
    }
}
