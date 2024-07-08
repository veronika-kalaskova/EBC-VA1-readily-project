package com.example.readily.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(var name: String, var author: String, var genre: String, var stars: Int) {

    @PrimaryKey
    var id: Long? = null

    var date: Long? = null

    var imageUri: String? = null

}