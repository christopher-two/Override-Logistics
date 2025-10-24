package org.override.logistics.presentation.features.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.override.logistics.core.presentation.InputField
import org.override.logistics.core.presentation.PasswordField
import org.override.logistics.core.presentation.PrimaryButton
import org.override.logistics.presentation.theme.DocsTheme

@Composable
fun LoginRoot(
    viewModel: LoginViewModel,
    onLoginSuccess: () -> Unit = {},
    onMessage: (String) -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    // Collect one-shot events from the ViewModel
    LaunchedEffect(viewModel) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginEvent.Success -> onLoginSuccess()
                is LoginEvent.Message -> onMessage(event.text)
            }
        }
    }

    LoginScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenido",
            style = typography.headlineLarge,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 40.sp
        )
        
        Spacer(modifier = Modifier.height(16.dp))

        InputField(
            value = state.username,
            onValueChange = { onAction(LoginAction.UpdateUsername(it)) },
            label = "Usuario"
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordField(
            value = state.password,
            onValueChange = { onAction(LoginAction.UpdatePassword(it)) },
            label = "Contraseña"
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!state.error.isNullOrBlank()) {
            Text(
                text = state.error,
                color = colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
        content = {
            Surface(
                color = colorScheme.surfaceContainerLowest,
                contentColor = colorScheme.onBackground,
                shadowElevation = 16.dp,
                shape = RectangleShape,
                border = BorderStroke(2.dp, colorScheme.onBackground),
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    PrimaryButton(
                        text = "Entrar",
                        onClick = { onAction(LoginAction.Submit) },
                        loading = state.loading
                    )
                }
            }
        }
    )
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun Preview() {
    DocsTheme(
        darkTheme = false
    ) {
        LoginScreen(
            state = LoginState(),
            onAction = {}
        )
    }
}