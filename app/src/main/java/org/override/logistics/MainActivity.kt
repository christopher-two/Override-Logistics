package org.override.logistics

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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.firstOrNull
import org.koin.compose.koinInject
import org.override.logistics.core.common.RoutesApp
import org.override.logistics.domain.session.usecase.IsUserLoggedInUseCase
import org.override.logistics.presentation.navigation.NavigationApp
import org.override.logistics.presentation.theme.DocsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val userLoggedInUseCase = koinInject<IsUserLoggedInUseCase>().invoke()
            var isLogged: Boolean? by remember { mutableStateOf(null) }

            LaunchedEffect(key1 = Unit) {
                isLogged = userLoggedInUseCase.firstOrNull() ?: false
            }

            splashScreen.setKeepOnScreenCondition { false }

            DocsTheme {
                when (isLogged) {
                    null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    true -> {
                        NavigationApp(
                            navController = rememberNavController(),
                            startDestination = RoutesApp.Warehouse.route
                        )
                    }

                    false -> {
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