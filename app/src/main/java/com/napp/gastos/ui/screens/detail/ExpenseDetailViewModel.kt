package com.napp.gastos.ui.screens.detail

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

data class ExpenseDetailUiState(
    val isLoading: Boolean = true,
    val expense: Expense? = null,
    val isDeleting: Boolean = false,
    val deleted: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ExpenseDetailViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseDetailUiState())
    val uiState: StateFlow<ExpenseDetailUiState> = _uiState.asStateFlow()

    fun loadExpense(expenseId: Long) {
        viewModelScope.launch {
            val expense = repository.getExpenseById(expenseId)
            _uiState.value = _uiState.value.copy(
                expense = expense,
                isLoading = false,
                error = if (expense == null) "Expense not found" else null
            )
        }
    }

    fun delete() {
        val expense = _uiState.value.expense ?: return
        _uiState.value = _uiState.value.copy(isDeleting = true)
        viewModelScope.launch {
            repository.delete(expense)
            _uiState.value = _uiState.value.copy(isDeleting = false, deleted = true)
        }
    }
}
