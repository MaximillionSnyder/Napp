package com.napp.gastos.di

import android.content.Context
import androidx.room.Room
import com.napp.gastos.data.local.ExpenseDao
import com.napp.gastos.data.local.NappDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NappDatabase {
        return Room.databaseBuilder(
            context,
            NappDatabase::class.java,
            "napp_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseDao(database: NappDatabase): ExpenseDao {
        return database.expenseDao()
    }
}
