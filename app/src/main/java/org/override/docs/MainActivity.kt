package org.override.docs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.firstOrNull
import org.koin.compose.koinInject
import org.override.docs.core.common.RoutesApp
import org.override.docs.domain.session.usecase.IsUserLoggedInUseCase
import org.override.docs.presentation.navigation.NavigationApp
import org.override.docs.presentation.theme.DocsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userLoggedInUseCase = koinInject<IsUserLoggedInUseCase>().invoke()

            // 1. Usa un Boolean nulable. 'null' será nuestro estado de carga.
            var isLogged: Boolean? by remember { mutableStateOf(null) }

            // 2. El LaunchedEffect sigue igual, pero ahora actualizará de 'null' a 'true' o 'false'.
            LaunchedEffect(key1 = Unit) {
                isLogged = userLoggedInUseCase.firstOrNull() ?: false
            }

            DocsTheme {
                // 3. Muestra contenido diferente según el estado.
                when (isLogged) {
                    null -> {
                        // Estado de carga: Muestra un indicador o tu splash screen.
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    true -> {
                        // Usuario logueado: Navega directamente a Home.
                        NavigationApp(
                            navController = rememberNavController(),
                            startDestination = RoutesApp.Home.route
                        )
                    }

                    false -> {
                        // Usuario no logueado: Navega directamente a Login.
                        NavigationApp(
                            navController = rememberNavController(),
                            startDestination = RoutesApp.Login.route
                        )
                    }
                }
            }
        }
    }
}