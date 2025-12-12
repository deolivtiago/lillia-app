package com.clarxlabs.lillia.ui.auth.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.components.ActionButton
import com.clarxlabs.lillia.ui.components.FormHeader
import com.clarxlabs.lillia.ui.components.PasswordFormField
import com.clarxlabs.lillia.ui.components.QuestionButton
import com.clarxlabs.lillia.ui.components.TextFormField
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun ProfileView(viewModel: ProfileViewModel, navigateTo: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sendEvent = viewModel::sendEvent

    ProfileViewContent(state, sendEvent, navigateTo)
}

@Composable
fun ProfileViewContent(
    state: ProfileModel.State,
    sendEvent: (ProfileModel.Event) -> Unit,
    navigateTo: (AppRoute) -> Unit,
) {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surfaceDim)
                .imePadding(),
        ) {
            Spacer(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxSize()
                    .weight(3f)
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topEnd = 32.dp,
                            bottomStart = 32.dp,
                            topStart = 4.dp,
                            bottomEnd = 4.dp,
                        )
                    )
                    .background(MaterialTheme.colorScheme.surfaceContainer)
                    .padding(bottom = 16.dp, top = 32.dp, start = 16.dp, end = 16.dp),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    FormHeader(title = "Alterar Cadastro")

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        QuestionButton(
                            questionText = "Precisa de ajuda?",
                            actionTitle = "Entrar em contato",
                            onClicked = {
                                sendEvent(ProfileModel.Event.OnContactClicked(navigateTo))
                            },
                            modifier = Modifier.align(Alignment.End),
                        )
                        QuestionButton(
                            questionText = "Deseja alterar a senha?",
                            actionTitle = "Alterar",
                            onClicked = {
                                sendEvent(ProfileModel.Event.OnResetPasswordClicked(navigateTo))
                            },
                            modifier = Modifier.align(Alignment.End),
                            isEnabled = !state.isLoading,
                        )
                    }
                }

                Column {
                    TextFormField(
                        value = state.fullName,
                        valueErrorMessage = state.fullNameError,
                        onValueChanged = {
                            sendEvent(ProfileModel.Event.OnFullNameChanged(it))
                        },
                        label = "Nome Completo",
                        isLoading = state.isLoading,
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Full name icon"
                            )
                        }
                    )

                    TextFormField(
                        value = state.email,
                        valueErrorMessage = state.emailError,
                        onValueChanged = {
                            sendEvent(ProfileModel.Event.OnEmailChanged(it))
                        },
                        isLoading = state.isLoading,
                    )

                    PasswordFormField(
                        value = state.password,
                        valueErrorMessage = state.passwordError,
                        onValueChanged = {
                            sendEvent(ProfileModel.Event.OnPasswordChanged(it))
                        },
                        isValueVisible = state.isPasswordVisible,
                        onToggleVisibility = {
                            sendEvent(ProfileModel.Event.OnPasswordVisibilityClicked)
                        },
                        isLoading = state.isLoading,
                    )

                    PasswordFormField(
                        value = state.passwordConfirmation,
                        valueErrorMessage = state.passwordConfirmationError,
                        onValueChanged = {
                            sendEvent(ProfileModel.Event.OnPasswordConfirmationChanged(it))
                        },
                        label = "Confirmação de Senha",
                        isValueVisible = state.isPasswordVisible,
                        onToggleVisibility = {
                            sendEvent(ProfileModel.Event.OnPasswordVisibilityClicked)
                        },
                        isLoading = state.isLoading,
                    )

                    ActionButton(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClicked = {
                            sendEvent(ProfileModel.Event.OnSubmitClicked(navigateTo))
                        },
                        isLoading = state.isLoading,
                        isEnabled = state.isFormValid,
                    )
                }

                QuestionButton(
                    questionText = "Já possui cadastro?",
                    actionTitle = "ENTRAR",
                    onClicked = {
                        sendEvent(ProfileModel.Event.OnSignInClicked(navigateTo))
                    },
                    isEnabled = !state.isLoading,
                )
            }
            Spacer(
                modifier = Modifier
                    .widthIn(max = 480.dp)
                    .fillMaxSize()
                    .weight(1f)
            )
        }
    }
}


@Preview(showSystemUi = true, device = "spec:parent=pixel_3a")
@Composable
fun PreviewPhone() {
    LilliaTheme {
        ProfileViewContent(
            state = ProfileModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun PreviewPhoneSmall() {
    LilliaTheme {
        ProfileViewContent(
            state = ProfileModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletPortrait() {
    LilliaTheme {
        ProfileViewContent(
            state = ProfileModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun PreviewTabletLandscape() {
    LilliaTheme {
        ProfileViewContent(
            state = ProfileModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}
