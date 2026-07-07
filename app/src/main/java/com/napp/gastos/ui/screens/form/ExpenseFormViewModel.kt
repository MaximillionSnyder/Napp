package com.napp.gastos.ui.screens.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.napp.gastos.data.local.Expense
import com.napp.gastos.data.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExpenseFormUiState(
    val amount: String = "",
    val category: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis(),
    val isEditing: Boolean = false,
    val isSaving: Boolean = false,
    val saveSuccess: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ExpenseFormViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseFormUiState())
    val uiState: StateFlow<ExpenseFormUiState> = _uiState.asStateFlow()

    private var existingExpenseId: Long? = null

    fun loadExpense(expenseId: Long?) {
        if (expenseId == null) return
        existingExpenseId = expenseId
        viewModelScope.launch {
            val expense = repository.getExpenseById(expenseId) ?: return@launch
            _uiState.value = _uiState.value.copy(
                amount = if (expense.amount == 0.0) "" else expense.amount.toString(),
                category = expense.category,
                description = expense.description,
                date = expense.date,
                isEditing = true
            )
        }
    }

    fun updateAmount(value: String) {
        _uiState.value = _uiState.value.copy(amount = value, error = null)
    }

    fun updateCategory(value: String) {
        _uiState.value = _uiState.value.copy(category = value, error = null)
    }

    fun updateDescription(value: String) {
        _uiState.value = _uiState.value.copy(description = value, error = null)
    }

    fun updateDate(value: Long) {
        _uiState.value = _uiState.value.copy(date = value)
    }

    fun save() {
        val state = _uiState.value
        val amount = state.amount.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _uiState.value = state.copy(error = "Enter a valid amount")
            return
        }
        if (state.category.isBlank()) {
            _uiState.value = state.copy(error = "Enter a category")
            return
        }

        _uiState.value = state.copy(isSaving = true, error = null)

        viewModelScope.launch {
            val expense = Expense(
                id = existingExpenseId ?: 0,
                amount = amount,
                category = state.category.trim(),
                description = state.description.trim(),
                date = state.date
            )
            if (existingExpenseId != null) {
                repository.update(expense)
            } else {
                repository.insert(expense)
            }
            _uiState.value = _uiState.value.copy(isSaving = false, saveSuccess = true)
        }
    }
}
