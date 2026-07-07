package com.napp.gastos.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class NappDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
