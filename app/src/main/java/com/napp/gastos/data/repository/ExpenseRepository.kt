package com.napp.gastos.data.repository

import com.napp.gastos.data.local.Expense
import com.napp.gastos.data.local.ExpenseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ExpenseRepository @Inject constructor(
    private val expenseDao: ExpenseDao
) {
    fun getAllExpenses(): Flow<List<Expense>> = expenseDao.getAllExpenses()

    suspend fun getExpenseById(id: Long): Expense? = expenseDao.getExpenseById(id)

    suspend fun insert(expense: Expense): Long = expenseDao.insert(expense)

    suspend fun update(expense: Expense) = expenseDao.update(expense)

    suspend fun delete(expense: Expense) = expenseDao.delete(expense)

    suspend fun deleteById(id: Long) = expenseDao.deleteById(id)

    fun getTotalAmount(): Flow<Double?> = expenseDao.getTotalAmount()
}
