package id.invi.kread

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppRoute {

    @Serializable
    data object Splash : AppRoute

    @Serializable
    data object Login : AppRoute

    @Serializable
    data object Register : AppRoute

    @Serializable
    data object Home : AppRoute
}