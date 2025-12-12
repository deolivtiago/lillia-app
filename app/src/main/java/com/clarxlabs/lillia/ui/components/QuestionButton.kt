package com.clarxlabs.lillia.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun QuestionButton(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {},
    questionText: String = "?",
    actionTitle: String = "OK",
    horizontalArrangement: Arrangement.HorizontalOrVertical = Arrangement.Center,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    isEnabled: Boolean = true,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment,
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
            text = questionText,
            style = MaterialTheme.typography.bodyLarge,
        )
        TextButton(
            modifier = Modifier.height(32.dp),
            onClick = onClicked,
            enabled = isEnabled,
            shape = MaterialTheme.shapes.small,
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            Text(
                text = actionTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                )
            )
        }
    }
}

@Preview
@Composable
fun QuestionButtonPreview() {
    LilliaTheme {
        Surface {
            QuestionButton(onClicked = {})
        }
    }
}
