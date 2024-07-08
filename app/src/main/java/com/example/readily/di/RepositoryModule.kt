package com.example.readily.di

import com.example.readily.database.BooksDao
import com.example.readily.database.ILocalBooksRepository
import com.example.readily.database.LocalBooksRepositoryImpl
import com.example.readily.model.Book
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideLocalTaskRepository(dao: BooksDao) : ILocalBooksRepository {
        return LocalBooksRepositoryImpl(dao)
    }
}