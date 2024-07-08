package com.example.readily.database

import com.example.readily.model.Book
import kotlinx.coroutines.flow.Flow

interface ILocalBooksRepository {

    fun getAll(): Flow<List<Book>>
    suspend fun insert(book: Book): Long
    suspend fun update(book: Book)
    suspend fun delete(book: Book): Int
    suspend fun getBook(id: Long): Book
    suspend fun getTotalBooks(): Int

}