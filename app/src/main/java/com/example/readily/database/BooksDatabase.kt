package com.example.readily.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.readily.model.Book

@Database(entities = [Book::class], version = 3, exportSchema = true)
abstract class BooksDatabase : RoomDatabase() {

    abstract fun booksDao(): BooksDao

    companion object {
        private var INSTANCE: BooksDatabase? = null
        fun getDatabase(context: Context): BooksDatabase {
            if (INSTANCE == null) {
                synchronized(BooksDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BooksDatabase::class.java, "books"
                        ).fallbackToDestructiveMigration().build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}