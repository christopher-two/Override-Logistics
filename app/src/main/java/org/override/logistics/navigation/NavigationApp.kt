package org.override.logistics.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import org.override.logistics.core.common.RoutesApp
import org.override.logistics.features.haulier.presentation.HaulierRoot
import org.override.logistics.features.login.presentation.LoginRoot
import org.override.logistics.features.wharehouse.presentation.WarehouseRoot

@Composable
fun NavigationApp(
    navController: NavHostController,
    startDestination: String = RoutesApp.Warehouse.route,
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

        composable(RoutesApp.Haulier.route) {
            HaulierRoot(
                viewModel = koinViewModel()
            )
        }
    }
}