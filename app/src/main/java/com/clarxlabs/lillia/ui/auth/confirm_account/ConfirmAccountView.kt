package com.clarxlabs.lillia.ui.auth.confirm_account

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
import com.clarxlabs.lillia.ui.components.QuestionButton
import com.clarxlabs.lillia.ui.components.TextFormField
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun ConfirmAccountView(viewModel: ConfirmAccountViewModel, navigateTo: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sendEvent = viewModel::sendEvent

    ConfirmAccountViewContent(state, sendEvent, navigateTo)
}

@Composable
fun ConfirmAccountViewContent(
    state: ConfirmAccountModel.State,
    sendEvent: (ConfirmAccountModel.Event) -> Unit,
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
                    FormHeader(title = "Confirmar Cadastro")

                    QuestionButton(
                        questionText = "Precisa de ajuda?",
                        actionTitle = "Entrar em contato",
                        onClicked = {
                            sendEvent(ConfirmAccountModel.Event.OnContactClicked(navigateTo))
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
                        ) { append("\ne informe abaixo o código de verificação\nrecebido para confirmar seu cadastro.") }
                    }
                )

                TextFormField(
                    value = state.code,
                    onValueChanged = {
                        sendEvent(ConfirmAccountModel.Event.OnCodeChanged(it))
                    },
                    label = "Código de Verificação",
                    isEnabled = !state.isLoading,
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                sendEvent(ConfirmAccountModel.Event.OnCodeChanged(""))
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

                ActionButton(
                    onClicked = {
                        sendEvent(ConfirmAccountModel.Event.OnSubmitClicked(navigateTo))
                    },
                    isLoading = state.isLoading,
                    actionTitle = "CONFIRMAR",
                )

                QuestionButton(
                    questionText = "Precisa de um novo código?",
                    actionTitle = "REENVIAR",
                    onClicked = {
                        sendEvent(ConfirmAccountModel.Event.OnSendCodeClicked(navigateTo))
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

@Preview(device = "spec:parent=small_phone,navigation=buttons", showSystemUi = true)
@Composable
private fun ConfirmViewContentPreview() {
    LilliaTheme {
        ConfirmAccountViewContent(
            state = ConfirmAccountModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}
