package id.invi.kread.presenter.addtracking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.invi.kread.domain.AppRepository
import id.invi.kread.domain.EventResult
import id.invi.kread.domain.checkResultNonComposable
import id.invi.kread.domain.model.HabitTracking
import id.invi.kread.domain.sendError
import id.invi.kread.domain.sendLoading
import id.invi.kread.domain.sendSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTrackingViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val _addTrackingEvent = Channel<EventResult<Any>>()
    val addTrackingEvent = _addTrackingEvent

    private val _updateTrackingEvent = Channel<EventResult<Unit>>()
    val updateTrackingEvent = _updateTrackingEvent

    fun addTracking(habitTracking: HabitTracking) {
        viewModelScope.launch {
            appRepository.addTracking(habitTracking).collectLatest { result ->
                result.checkResultNonComposable(
                    onLoading = {
                        _addTrackingEvent.sendLoading()
                    },
                    onError = { ex ->
                        _addTrackingEvent.sendError(ex)
                    },
                    onSuccess = {
                        _addTrackingEvent.sendSuccess(it)
                    }
                )
            }
        }
    }

    fun updateTracking(habitTracking: HabitTracking) {
        viewModelScope.launch {
            appRepository.updateTracking(habitTracking)
                .collectLatest { result ->
                    result.checkResultNonComposable(
                        onLoading = {
                            _updateTrackingEvent.sendLoading()
                        },
                        onError = { ex ->
                            _updateTrackingEvent.sendError(ex)
                        },
                        onSuccess = {
                            _updateTrackingEvent.sendSuccess(it)
                        }
                    )
                }
        }
    }
}
