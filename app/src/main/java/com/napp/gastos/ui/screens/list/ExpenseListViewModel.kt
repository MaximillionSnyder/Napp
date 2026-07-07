package com.napp.gastos.ui.screens.list

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

data class ExpenseListUiState(
    val expenses: List<Expense> = emptyList(),
    val totalAmount: Double = 0.0,
    val isLoading: Boolean = true
)

@HiltViewModel
class ExpenseListViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpenseListUiState())
    val uiState: StateFlow<ExpenseListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllExpenses().collect { expenses ->
                _uiState.value = _uiState.value.copy(
                    expenses = expenses,
                    isLoading = false
                )
            }
        }
        viewModelScope.launch {
            repository.getTotalAmount().collect { total ->
                _uiState.value = _uiState.value.copy(
                    totalAmount = total ?: 0.0
                )
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.delete(expense)
        }
    }
}
