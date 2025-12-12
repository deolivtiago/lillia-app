package com.clarxlabs.lillia

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.clarxlabs.lillia.core.datasources.AuthenticationDataSource
import com.clarxlabs.lillia.core.datasources.AuthenticationDataSourceImpl
import com.clarxlabs.lillia.core.datasources.factories.HttpClientFactory
import com.clarxlabs.lillia.core.repositories.AuthenticationRepository
import com.clarxlabs.lillia.core.repositories.AuthenticationRepositoryImpl
import com.clarxlabs.lillia.core.services.ValidationService
import com.clarxlabs.lillia.core.services.ValidationServiceImpl
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.auth.confirm_account.ConfirmAccountView
import com.clarxlabs.lillia.ui.auth.confirm_account.ConfirmAccountViewModel
import com.clarxlabs.lillia.ui.auth.profile.ProfileView
import com.clarxlabs.lillia.ui.auth.profile.ProfileViewModel
import com.clarxlabs.lillia.ui.auth.reset_password.ResetPasswordView
import com.clarxlabs.lillia.ui.auth.reset_password.ResetPasswordViewModel
import com.clarxlabs.lillia.ui.auth.sign_in.SignInView
import com.clarxlabs.lillia.ui.auth.sign_in.SignInViewModel
import com.clarxlabs.lillia.ui.auth.sign_up.SignUpView
import com.clarxlabs.lillia.ui.auth.sign_up.SignUpViewModel
import com.clarxlabs.lillia.ui.auth.verify_account.VerifyAccountView
import com.clarxlabs.lillia.ui.auth.verify_account.VerifyAccountViewModel
import com.clarxlabs.lillia.ui.home.HomeView
import com.clarxlabs.lillia.ui.home.HomeViewModel
import com.clarxlabs.lillia.ui.theme.LilliaTheme
import com.clarxlabs.lillia.ui.users.list.ListView
import com.clarxlabs.lillia.ui.users.list.ListViewModel
import io.ktor.client.HttpClient
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module


@Composable
fun MainApplication() {
    val appModule = module {
        singleOf(HttpClientFactory::create).bind<HttpClient>()
        singleOf(::AuthenticationDataSourceImpl).bind<AuthenticationDataSource>()
        singleOf(::AuthenticationRepositoryImpl).bind<AuthenticationRepository>()
        singleOf(::ValidationServiceImpl).bind<ValidationService>()

        viewModelOf(::SignInViewModel)
        viewModelOf(::SignUpViewModel)
        viewModelOf(::VerifyAccountViewModel)
        viewModelOf(::ConfirmAccountViewModel)
        viewModelOf(::ResetPasswordViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::ListViewModel)
    }

    KoinApplication({ modules(appModule) }) {
        LilliaTheme {
            val navController = rememberNavController()
            NavHost(navController, AppRoute.AuthGraph) {
                navigation<AppRoute.AuthGraph>(startDestination = AppRoute.SignIn) {
                    composable<AppRoute.SignIn> {
                        SignInView(
                            viewModel = koinViewModel<SignInViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.SignUp> {
                        SignUpView(
                            viewModel = koinViewModel<SignUpViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.SendVerification> {
                        VerifyAccountView(
                            viewModel = koinViewModel<VerifyAccountViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.ConfirmAccount> {
                        ConfirmAccountView(
                            viewModel = koinViewModel<ConfirmAccountViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.ResetPassword> {
                        ResetPasswordView(
                            viewModel = koinViewModel<ResetPasswordViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.Home> {
                        HomeView(
                            viewModel = koinViewModel<HomeViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                    composable<AppRoute.ListUsers> {
                        ListView(
                            viewModel = koinViewModel<ListViewModel>(),
                            onNavigate = navController::navigate,
                        )
                    }
                    composable<AppRoute.Profile> {
                        ProfileView(
                            viewModel = koinViewModel<ProfileViewModel>(),
                            navigateTo = navController::navigate,
                        )
                    }
                }
            }
        }
    }
}
