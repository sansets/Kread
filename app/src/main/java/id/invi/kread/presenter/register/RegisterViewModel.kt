package id.invi.kread.presenter.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.invi.kread.domain.AppRepository
import id.invi.kread.domain.EventResult
import id.invi.kread.domain.checkResultNonComposable
import id.invi.kread.domain.sendError
import id.invi.kread.domain.sendLoading
import id.invi.kread.domain.sendSuccess
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : ViewModel() {

    private val _registerEvent = Channel<EventResult<Any>>()
    val registerEvent = _registerEvent

    fun register(
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            appRepository.register(
                email = email,
                password = password,
            ).collectLatest { result ->
                result.checkResultNonComposable(
                    onLoading = {
                        _registerEvent.sendLoading()
                    },
                    onError = { ex ->
                        _registerEvent.sendError(ex)
                    },
                    onSuccess = {
                        _registerEvent.sendSuccess(it)
                    }
                )
            }
        }
    }
}
