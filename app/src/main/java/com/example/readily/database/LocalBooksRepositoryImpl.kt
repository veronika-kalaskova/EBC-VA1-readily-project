package com.example.readily.database

import com.example.readily.model.Book
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalBooksRepositoryImpl @Inject constructor(private val dao: BooksDao) : ILocalBooksRepository {
    override fun getAll(): Flow<List<Book>> {
        return dao.getAll()
    }

    override suspend fun insert(book: Book): Long {
        return dao.insert(book)
    }

    override suspend fun update(book: Book) {
        return dao.update(book)
    }

    override suspend fun delete(book: Book): Int {
        return dao.delete(book)
    }

    override suspend fun getBook(id: Long): Book {
        return dao.getBook(id)
    }

    override suspend fun getTotalBooks(): Int {
        return dao.getTotalBooks()
    }


}