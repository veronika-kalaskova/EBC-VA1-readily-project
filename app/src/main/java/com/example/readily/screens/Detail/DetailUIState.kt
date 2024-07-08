package com.example.readily.screens.Detail

import com.example.readily.screens.ScreenData

sealed class DetailUIState {
    class Loading : DetailUIState()
    class BookDeleted : DetailUIState()
    class ScreenDataChanged(val data: ScreenData) : DetailUIState()

}