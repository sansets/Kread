package id.invi.kread

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import id.invi.kread.presenter.home.HomeRoot
import id.invi.kread.presenter.login.LoginRoot
import id.invi.kread.presenter.register.RegisterRoot
import id.invi.kread.presenter.splash.SplashRoot
import id.invi.kread.ui.theme.KreadTheme

@AndroidEntryPoint
class AppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KreadTheme {
                val backStack = remember { mutableStateListOf<AppRoute>(AppRoute.Splash) }

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    entryDecorators = listOf(
                        rememberSaveableStateHolderNavEntryDecorator(),
                        rememberViewModelStoreNavEntryDecorator()
                    ),
                    entryProvider = { key ->
                        when (key) {
                            is AppRoute.Splash -> NavEntry(key) {
                                SplashRoot(
                                    onAuthenticationCheck = {
                                        backStack.clear()
                                        if (it) {
                                            backStack.add(AppRoute.Home)
                                        } else {
                                            backStack.add(AppRoute.Login)
                                        }
                                    },
                                )
                            }

                            is AppRoute.Login -> NavEntry(key) {
                                LoginRoot(
                                    onRegisterClick = {
                                        backStack.add(AppRoute.Register)
                                    },
                                    onLoginSuccess = {
                                        backStack.clear()
                                        backStack.add(AppRoute.Home)
                                    }
                                )
                            }

                            is AppRoute.Register -> NavEntry(key) {
                                RegisterRoot(
                                    onBackNavigateClick = {
                                        backStack.removeLastOrNull()
                                    },
                                    onRegisterSuccess = {
                                        backStack.clear()
                                        backStack.add(AppRoute.Home)
                                    }
                                )
                            }

                            is AppRoute.Home -> NavEntry(key) {
                                HomeRoot(
                                    onLogoutSuccess = {
                                        backStack.clear()
                                        backStack.add(AppRoute.Login)
                                    },
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}