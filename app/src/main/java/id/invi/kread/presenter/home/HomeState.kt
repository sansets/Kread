package id.invi.kread.presenter.home

import id.invi.kread.domain.Result
import id.invi.kread.domain.model.HabitTracking

data class HomeState(
    val isShowPullToRefreshIndicator: Boolean = false,
    val habitTrackings: Result<List<Pair<HabitTracking, Result<Unit>>>> = Result.Default,
)