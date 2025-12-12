package com.clarxlabs.lillia.ui.auth.verify_account

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
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun VerifyAccountView(viewModel: VerifyAccountViewModel, navigateTo: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sendEvent = viewModel::sendEvent

    VerifyAccountViewContent(state, sendEvent, navigateTo)
}

@Composable
fun VerifyAccountViewContent(
    state: VerifyAccountModel.State,
    sendEvent: (VerifyAccountModel.Event) -> Unit,
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
                    FormHeader(title = "Verificar Cadastro")

                    QuestionButton(
                        questionText = "Precisa de ajuda?",
                        actionTitle = "Entrar em contato",
                        onClicked = {
                            sendEvent(VerifyAccountModel.Event.OnContactClicked(navigateTo))
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
                        ) { append("Precisamos verificar o email\n") }
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
                        ) { append("\npara confirmar seu cadastro.\nAo continuar, você receberá um email\ncom o código de verificação.") }
                    }
                )

                ActionButton(
                    onClicked = {
                        sendEvent(VerifyAccountModel.Event.OnSubmitClicked(navigateTo))
                    },
                    isLoading = state.isLoading,
                    actionTitle = "CONTINUAR"
                )

                QuestionButton(
                    questionText = "Já possui um código?",
                    actionTitle = "CONFIRMAR",
                    onClicked = {
                        sendEvent(VerifyAccountModel.Event.OnConfirmClicked(navigateTo))
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
fun VerifyPreviewPhone() {
    LilliaTheme {
        VerifyAccountViewContent(
            state = VerifyAccountModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun VerifyPreviewPhoneSmall() {
    LilliaTheme {
        VerifyAccountViewContent(
            state = VerifyAccountModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true)
@Composable
fun VerifyPreviewTabletPortrait() {
    LilliaTheme {
        VerifyAccountViewContent(
            state = VerifyAccountModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun VerifyPreviewTabletLandscape() {
    LilliaTheme {
        VerifyAccountViewContent(
            state = VerifyAccountModel.State(),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

