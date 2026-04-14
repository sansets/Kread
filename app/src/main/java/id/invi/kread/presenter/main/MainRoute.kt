package id.invi.kread.presenter.main

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainRoute {

    @Serializable
    data object Home : MainRoute

    @Serializable
    data object Profile : MainRoute
}