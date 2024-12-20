package com.ioline.tradebot.features.authorization.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ioline.tradebot.R
import com.ioline.tradebot.features.authorization.presentation.AuthorizationEvent
import com.ioline.tradebot.features.authorization.presentation.AuthorizationState

@Composable
internal fun AuthorizationView(state: AuthorizationState, onEvent: (AuthorizationEvent) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(AuthorizationEvent.Ui.Action.EmailChanged(it)) },
            label = { Text(stringResource(R.string.authorization_email_label)) },
            isError = state.emailError != null,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        state.emailError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        PasswordForm(
            state.password,
            state.passwordError,
            { onEvent(AuthorizationEvent.Ui.Action.PasswordChanged(it)) },
            "Password"
        )
        if (state.registrationMode) {
            PasswordForm(
                state.confirmPassword,
                state.passwordConfirmError,
                { onEvent(AuthorizationEvent.Ui.Action.PasswordConfirmChanged(it)) },
                "Confirm password"
            )
        }
        state.passwordError?.let { error ->
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { /* Реализуйте восстановление пароля */ },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Forget password?")
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onEvent(AuthorizationEvent.Ui.Click.Login) },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isFormValid
        ) {
            if (state.registrationMode) {
                Text(stringResource(R.string.authorization_button_create_account))
            } else {
                Text(stringResource(R.string.authorization_button_log_in))
            }
        }

        Button(
            onClick = {
                if (state.registrationMode) {
                    onEvent(AuthorizationEvent.Ui.Click.GoToLogin)
                } else {
                    onEvent(AuthorizationEvent.Ui.Click.GoToRegistration)
                }
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            if (state.registrationMode) {
                Text(stringResource(R.string.authorization_second_button_registration))
            } else {
                Text(stringResource(R.string.authorization_second_button_login))
            }
        }
    }
}

@Composable
private fun PasswordForm(
    password: String,
    passwordError: String?,
    onChange: (String) -> Unit,
    label: String
) {
    var passwordVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = password,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        isError = passwordError != null,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = null)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview
@Composable
private fun LoginPreview() {
    AuthorizationView(AuthorizationState()) { }
}

@Preview
@Composable
private fun RegistrationPreview() {
    AuthorizationView(AuthorizationState(registrationMode = true)) { }
}