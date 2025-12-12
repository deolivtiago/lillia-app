package com.clarxlabs.lillia.ui.auth.reset_password

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
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
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
fun ResetPasswordView(viewModel: ResetPasswordViewModel, navigateTo: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sendEvent = viewModel::sendEvent

    ResetPasswordViewContent(state, sendEvent, navigateTo)
}

@Composable
private fun ResetPasswordViewContent(
    state: ResetPasswordModel.State = ResetPasswordModel.State(),
    sendEvent: (ResetPasswordModel.Event) -> Unit = {},
    navigateTo: (AppRoute) -> Unit = {},
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
                    FormHeader(title = "Cadastrar Senha")

                    QuestionButton(
                        questionText = "Precisa de ajuda?",
                        actionTitle = "Entrar em contato",
                        onClicked = {
                            sendEvent(ResetPasswordModel.Event.OnContactClicked(navigateTo))
                        },
                        modifier = Modifier.align(Alignment.End),
                    )
                }

                Text(
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                color = MaterialTheme.typography.bodyLarge.color,
                            )
                        ) { append("Por favor, acesse o email\n") }
                        withStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                letterSpacing = TextUnit(1.5F, TextUnitType.Sp),
                            )
                        ) { append(state.email) }
                        withStyle(
                            SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                                color = MaterialTheme.typography.bodyLarge.color,
                            )
                        ) { append("\ne informe abaixo o código de verificação\nrecebido para cadastrar a nova senha.") }
                    }
                )

                Column {
                    TextFormField(
                        value = state.code,
                        onValueChanged = {
                            sendEvent(ResetPasswordModel.Event.OnCodeChanged(it))
                        },
                        label = "Código de Verificação",
                        isEnabled = !state.isLoading,
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    sendEvent(ResetPasswordModel.Event.OnCodeChanged(""))
                                },
                                enabled = !state.isLoading,
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = "Code text reset",
                                )
                            }
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Password,
                                contentDescription = "Verification code icon"
                            )
                        }
                    )

                    PasswordFormField(
                        value = state.password,
                        valueErrorMessage = state.passwordError,
                        onValueChanged = {
                            sendEvent(ResetPasswordModel.Event.OnPasswordChanged(it))
                        },
                        label = "Nova senha",
                        isValueVisible = state.isPasswordVisible,
                        onToggleVisibility = {
                            sendEvent(ResetPasswordModel.Event.OnTogglePasswordVisibility)
                        },
                        isLoading = state.isLoading,
                    )

                    ActionButton(
                        actionTitle = "CONFIRMAR",
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClicked = {
                            sendEvent(ResetPasswordModel.Event.OnSubmitClicked(navigateTo))
                        },
                        isLoading = state.isLoading,
                        isEnabled = state.isFormValid,
                    )
                }

                QuestionButton(
                    questionText = "Precisa de um novo código?",
                    actionTitle = "REENVIAR",
                    onClicked = {
                        sendEvent(ResetPasswordModel.Event.OnSendCodeClicked(navigateTo))
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
fun ResetPasswordPreviewPhone() {
    LilliaTheme { ResetPasswordViewContent() }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun ResetPasswordPreviewPhoneSmall() {
    LilliaTheme { ResetPasswordViewContent() }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun ResetPasswordPreviewTabletPortrait() {
    LilliaTheme { ResetPasswordViewContent() }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun ResetPasswordPreviewTabletLandscape() {
    LilliaTheme { ResetPasswordViewContent() }
}
