package com.napp.gastos.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.napp.gastos.ui.screens.detail.ExpenseDetailScreen
import com.napp.gastos.ui.screens.form.ExpenseFormScreen
import com.napp.gastos.ui.screens.list.ExpenseListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.ExpenseList.route
    ) {
        composable(NavRoutes.ExpenseList.route) {
            ExpenseListScreen(
                onNavigateToForm = { expenseId ->
                    navController.navigate(NavRoutes.ExpenseForm.createRoute(expenseId))
                }
            )
        }

        composable(
            route = NavRoutes.ExpenseForm.route,
            arguments = listOf(
                navArgument("expenseId") {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) { backStackEntry ->
            val expenseId = backStackEntry.arguments?.getLong("expenseId") ?: -1L
            ExpenseFormScreen(
                expenseId = if (expenseId == -1L) null else expenseId,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
