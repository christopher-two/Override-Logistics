package org.override.docs.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.override.docs.core.common.RoutesApp
import org.override.docs.presentation.features.login.LoginRoot
import org.override.docs.presentation.features.warehouse.presentation.WarehouseRoot

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
                    navController.navigate(RoutesApp.Warehouse.route) {
                        popUpTo(RoutesApp.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(RoutesApp.Warehouse.route) {
            WarehouseRoot(
                viewModel = koinViewModel()
            )
        }
    }
}