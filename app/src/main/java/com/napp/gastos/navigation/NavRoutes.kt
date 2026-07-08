package com.napp.gastos.navigation

sealed class NavRoutes(val route: String) {
    data object ExpenseList : NavRoutes("expense_list")
    data object ExpenseForm : NavRoutes("expense_form?expenseId={expenseId}") {
        fun createRoute(expenseId: Long? = null): String {
            return if (expenseId != null) "expense_form?expenseId=$expenseId"
            else "expense_form"
        }
    }
    data object ExpenseDetail : NavRoutes("expense_detail/{expenseId}") {
        fun createRoute(expenseId: Long): String = "expense_detail/$expenseId"
    }
}
