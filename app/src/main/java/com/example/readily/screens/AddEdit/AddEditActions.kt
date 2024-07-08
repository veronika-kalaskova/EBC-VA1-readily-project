package com.example.readily.screens.AddEdit

interface AddEditActions {
    fun saveBook()
    fun loadBook(id: Long?)
    fun bookNameChanged(name: String)
    fun bookAuthorChanged(author: String)
    fun bookGenreChanged(genre: String)
    fun bookStarsChanged(stars: Int)
    fun bookDateChanged(date: Long?)
    fun deleteTask()
}