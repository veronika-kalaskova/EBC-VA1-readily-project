package com.example.readily.screens

import com.example.readily.model.Book

sealed class MainUIState {
    class Loading : MainUIState()
    class Success(val books: List<Book>) : MainUIState()
}