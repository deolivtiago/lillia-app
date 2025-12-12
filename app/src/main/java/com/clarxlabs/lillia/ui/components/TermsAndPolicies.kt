package com.clarxlabs.lillia.ui.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun TermsAndPolicies(
    modifier: Modifier = Modifier,
    termsUrl: String = "https://developer.android.com/",
    policiesUrl: String = "https://developer.android.com/jetpack/compose",
    verticalArrangement: Arrangement.Vertical = Arrangement.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Column(
        modifier = modifier,
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment,
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        color = MaterialTheme.typography.bodyLarge.color,
                    )
                ) { append("Ao continuar, você está aceitando nossos\n") }
                withLink(
                    LinkAnnotation.Url(
                        termsUrl,
                        TextLinkStyles(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline,
                            ),
                        )
                    )
                ) { append("Termos de Uso") }
                withStyle(
                    style = SpanStyle(
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
                        color = MaterialTheme.typography.bodyLarge.color,
                    )
                ) { append(" e ") }
                withLink(
                    LinkAnnotation.Url(
                        policiesUrl,
                        TextLinkStyles(
                            style = SpanStyle(
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                fontWeight = FontWeight.Black,
                                color = MaterialTheme.colorScheme.primary,
                                textDecoration = TextDecoration.Underline,
                            ),
                        )
                    )
                ) { append("Política de Privacidade") }
            }
        )
    }
}

@Preview()
@Composable
fun TermsAndPoliciesPreview() {
    LilliaTheme {
        Surface {
            TermsAndPolicies()
        }
    }
}
