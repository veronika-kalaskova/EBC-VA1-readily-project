package com.example.readily.screens.Challenge

import com.example.readily.model.Book
import com.example.readily.screens.AddEdit.AddEditUIState
import com.example.readily.screens.ScreenData

sealed class BookChallengeUIState {
    class Loading : BookChallengeUIState()
    class ScreenDataChanged(val data: BookChallengeData) : BookChallengeUIState()
    class Success(val number: Int) : BookChallengeUIState()

}