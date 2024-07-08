package com.example.readily.screens.AddEdit

import com.example.readily.screens.ScreenData

sealed class AddEditUIState {
    class Loading : AddEditUIState()
    class BookSaved : AddEditUIState()
    class BookDeleted: AddEditUIState()
    class ScreenDataChanged(val data: ScreenData) : AddEditUIState()
}