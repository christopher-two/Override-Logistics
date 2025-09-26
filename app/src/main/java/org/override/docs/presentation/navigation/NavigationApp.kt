package org.override.docs.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.override.docs.core.common.RoutesApp
import org.override.docs.presentation.features.login.LoginRoot

@Composable
fun NavigationApp(
    navController: NavHostController,
    startDestination: String = RoutesApp.Login.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(RoutesApp.Login.route) {
            LoginRoot(
                viewModel = koinViewModel(),
                onLoginSuccess = {
                    navController.navigate(RoutesApp.Home.route) {
                        popUpTo(RoutesApp.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(RoutesApp.Home.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Home",
                    fontSize = 40.sp
                )
            }
        }
    }
}