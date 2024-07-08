package com.example.readily.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readily.database.ILocalBooksRepository
import com.example.readily.screens.Challenge.BookChallengeUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: ILocalBooksRepository) : ViewModel() {

    //val mainUIState: MutableState<MainUIState> = mutableStateOf(MainUIState.Loading())

    private val _mainUIState: MutableStateFlow<MainUIState> = MutableStateFlow(value = MainUIState.Loading())

    val mainUIState = _mainUIState.asStateFlow()

    fun loadBooks() {
        viewModelScope.launch {
            repository.getAll().collect { book ->
                _mainUIState.update {
                    MainUIState.Success(book)
                }
            }
        }
    }

}