package com.example.readily.di

import com.example.readily.database.BooksDao
import com.example.readily.database.BooksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    @Singleton
    fun provideCarsDao(database: BooksDatabase) : BooksDao {
        return database.booksDao()
    }
}