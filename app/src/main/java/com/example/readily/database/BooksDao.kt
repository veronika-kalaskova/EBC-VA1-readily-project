package com.example.readily.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.readily.model.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BooksDao {
    @Query("SELECT * FROM books")
    fun getAll(): Flow<List<Book>>

    @Insert
    suspend fun insert(book: Book): Long

    @Update
    suspend fun update(book: Book)

    @Delete
    suspend fun delete(book: Book): Int

    @Query("SELECT * FROM books WHERE id = :id")
    suspend fun getBook(id: Long): Book

    @Query("SELECT COUNT(*) FROM books WHERE strftime('%Y', datetime(date / 1000, 'unixepoch')) = strftime('%Y', 'now')")
    suspend fun getTotalBooks(): Int


}