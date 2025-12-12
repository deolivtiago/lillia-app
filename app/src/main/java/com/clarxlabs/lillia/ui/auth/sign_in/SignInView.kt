package com.clarxlabs.lillia.ui.auth.sign_in

import android.content.res.Configuration
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clarxlabs.lillia.ui.AppRoute
import com.clarxlabs.lillia.ui.components.ActionButton
import com.clarxlabs.lillia.ui.components.FormHeader
import com.clarxlabs.lillia.ui.components.PasswordFormField
import com.clarxlabs.lillia.ui.components.QuestionButton
import com.clarxlabs.lillia.ui.components.TermsAndPolicies
import com.clarxlabs.lillia.ui.components.TextFormField
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun SignInView(viewModel: SignInViewModel, navigateTo: (AppRoute) -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val sendEvent = viewModel::sendEvent

    SignInViewContent(state, sendEvent, navigateTo)
}

@Composable
fun SignInViewContent(
    state: SignInModel.State,
    sendEvent: (SignInModel.Event) -> Unit,
    navigateTo: (AppRoute) -> Unit,
) {
    Surface(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surfaceDim)
                .imePadding(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
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
                    FormHeader(title = "Acessar Cadastro")

                    Column(
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        QuestionButton(
                            questionText = "Precisa de ajuda?",
                            actionTitle = buildAnnotatedString {
                                withLink(
                                    LinkAnnotation.Url(
                                        "https://developer.android.com/",
                                        TextLinkStyles(
                                            style = SpanStyle(
                                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                                fontWeight = FontWeight.Black,
                                                color = MaterialTheme.colorScheme.primary,
                                                textDecoration = TextDecoration.Underline,
                                            ),
                                        )
                                    )
                                ) { append("Entrar em contato") }
                            }.text,
                            onClicked = {
                                sendEvent(SignInModel.Event.OnContactClicked)
                            },
                            modifier = Modifier.align(Alignment.End),
                        )
                        QuestionButton(
                            questionText = "Esqueceu sua senha?",
                            actionTitle = "Recuperar",
                            onClicked = {
                                sendEvent(SignInModel.Event.OnResetPasswordClicked(navigateTo))
                            },
                            modifier = Modifier.align(Alignment.End),
                            isEnabled = !state.isLoading,
                        )
                    }
                }

                Column {
                    TextFormField(
                        value = state.email,
                        valueErrorMessage = state.emailError,
                        onValueChanged = {
                            sendEvent(SignInModel.Event.OnEmailChanged(it))
                        },
                        isLoading = state.isLoading,
                    )

                    PasswordFormField(
                        value = state.password,
                        valueErrorMessage = state.passwordError,
                        onValueChanged = {
                            sendEvent(SignInModel.Event.OnPasswordChanged(it))
                        },
                        isValueVisible = state.isPasswordVisible,
                        onToggleVisibility = {
                            sendEvent(SignInModel.Event.OnPasswordVisibilityClicked)
                        },
                        isLoading = state.isLoading,
                    )

                    ActionButton(
                        modifier = Modifier.padding(vertical = 8.dp),
                        onClicked = {
                            sendEvent(SignInModel.Event.OnSubmitClicked(navigateTo))
                        },
                        isLoading = state.isLoading,
                        isEnabled = state.isFormValid,
                    )
                }

                TermsAndPolicies()

                QuestionButton(
                    questionText = "Não possui cadastro?",
                    actionTitle = "CADASTRAR",
                    onClicked = {
                        sendEvent(SignInModel.Event.OnSignUpClicked(navigateTo))
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
fun SignInPreviewPhone() {
    LilliaTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(showSystemUi = true, device = "spec:parent=Galaxy Nexus,navigation=buttons")
@Composable
fun SignInPreviewPhoneSmall() {
    LilliaTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(
    device = "spec:parent=Nexus 7 2013,navigation=buttons", showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun SignInPreviewTabletPortrait() {
    LilliaTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            sendEvent = {},
            navigateTo = {},
        )
    }
}

@Preview(device = "spec:parent=Nexus 10,navigation=buttons", showSystemUi = true)
@Composable
fun SignInPreviewTabletLandscape() {
    LilliaTheme {
        SignInViewContent(
            state = SignInModel.State(email = "alice@wonderland.co"),
            sendEvent = {},
            navigateTo = {},
        )
    }
}
