package com.example.readily.screens.Challenge

interface BookChallengeActions {
    fun loadTotalBooks()
    fun wantToReadChanged(wantToRead: String)
    fun displayProgressChanged(displayProgress: Boolean)
    fun displayTextFieldChanged(displayTextField: Boolean)
}