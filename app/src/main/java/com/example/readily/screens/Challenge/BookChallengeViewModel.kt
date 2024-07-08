package com.example.readily.screens.Challenge

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readily.database.ILocalBooksRepository
import com.example.readily.screens.AddEdit.AddEditUIState
import com.example.readily.screens.Detail.DetailActions
import com.example.readily.screens.Detail.DetailUIState
import com.example.readily.screens.MainUIState
import com.example.readily.screens.ScreenData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookChallengeViewModel @Inject constructor(private val repository: ILocalBooksRepository) : ViewModel(),
    BookChallengeActions {

    private val data: BookChallengeData = BookChallengeData()

    private val _bookChallengeUIState: MutableStateFlow<BookChallengeUIState> = MutableStateFlow(value = BookChallengeUIState.Loading())

    val bookChallengeUIState = _bookChallengeUIState.asStateFlow()

    override fun loadTotalBooks() {
        viewModelScope.launch {
            val totalBooks = repository.getTotalBooks()
            _bookChallengeUIState.update {
                BookChallengeUIState.Success(totalBooks)
            }
        }
    }

    override fun displayTextFieldChanged(displayTextField: Boolean) {
        data.displayTextField = displayTextField
        _bookChallengeUIState.update {
            BookChallengeUIState.ScreenDataChanged(data)
        }
    }

    override fun displayProgressChanged(displayProgress: Boolean) {
        data.displayProgress = displayProgress
        _bookChallengeUIState.update {
            BookChallengeUIState.ScreenDataChanged(data)
        }
    }

    override fun wantToReadChanged(wantToRead: String) {
        data.wantToRead = wantToRead
        _bookChallengeUIState.update {
            BookChallengeUIState.ScreenDataChanged(data)
        }
    }


}