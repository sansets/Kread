package id.invi.kread.presenter.splash

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
class SplashViewModel @Inject constructor(
    private val appRepository: AppRepository,
) : ViewModel() {

    private val _checkIsLoggedInEvent = Channel<EventResult<Boolean>>()
    val checkIsLoggedInEvent = _checkIsLoggedInEvent

    init {
        checkIsLoggedIn()
    }

    fun checkIsLoggedIn() {
        viewModelScope.launch {
            appRepository.checkIsLoggedIn()
                .collectLatest { result ->
                    result.checkResultNonComposable(
                        onLoading = {
                            _checkIsLoggedInEvent.sendLoading()
                        },
                        onError = { ex ->
                            _checkIsLoggedInEvent.sendError(ex)
                        },
                        onSuccess = {
                            _checkIsLoggedInEvent.sendSuccess(it)
                        }
                    )
                }
        }
    }
}
