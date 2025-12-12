package com.clarxlabs.lillia.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.clarxlabs.lillia.ui.theme.LilliaTheme

@Composable
fun PasswordFormField(
    modifier: Modifier = Modifier,
    value: String = "",
    valueErrorMessage: String = "",
    onValueChanged: (String) -> Unit = {},
    isValueVisible: Boolean = false,
    label: String = "Senha",
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    onToggleVisibility: () -> Unit = {},
    leadingIcon: @Composable (() -> Unit) = {
        Icon(imageVector = Icons.Default.Lock, contentDescription = "Password icon")
    },
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        isError = valueErrorMessage.isNotEmpty(),
        supportingText = { Text(text = valueErrorMessage).takeUnless { valueErrorMessage.isEmpty() } },
        label = { Text(label) },
        shape = MaterialTheme.shapes.large,
        enabled = !isLoading and isEnabled,
        leadingIcon = leadingIcon,
        visualTransformation = if (isValueVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        trailingIcon = {
            IconButton(onClick = onToggleVisibility, enabled = !isLoading and isEnabled) {
                Icon(
                    imageVector = if (isValueVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                    contentDescription = "Visibility toggle",
                )
            }
        },
        maxLines = 1,
        modifier = modifier.fillMaxWidth(),
    )
}

@Preview
@Composable
private fun PasswordFormFieldPreview() {
    LilliaTheme { Surface { PasswordFormField() } }
}
