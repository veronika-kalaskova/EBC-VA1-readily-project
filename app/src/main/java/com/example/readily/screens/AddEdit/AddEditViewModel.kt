package com.example.readily.screens.AddEdit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readily.R
import com.example.readily.database.ILocalBooksRepository
import com.example.readily.screens.Detail.DetailUIState
import com.example.readily.screens.ScreenData
import com.example.readily.utils.LanguageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(private val repository: ILocalBooksRepository) : ViewModel(), AddEditActions {

    private var data: ScreenData = ScreenData()

    private val _addEditUIState: MutableStateFlow<AddEditUIState> = MutableStateFlow(value = AddEditUIState.Loading())

    val addEditUIState = _addEditUIState.asStateFlow()

    private val _detailUIState: MutableStateFlow<DetailUIState> = MutableStateFlow(value = DetailUIState.Loading())

    val detailUIState = _detailUIState.asStateFlow()

    var formError = mutableStateOf("")

    private fun validateForm(): Boolean {
        if (data.book.name.isBlank() || data.book.author.isBlank() || data.book.genre.isBlank() || data.book.stars <= 0) {
            if (LanguageUtils.isLanguageCloseToCzech()) {
                formError.value = "Všechna pole musí být vyplněna"
            } else {
                formError.value = "All fields must be filled in"
            }
            return false
        }
        formError.value = ""
        return true
    }

    override fun saveBook() {
        if (validateForm()) {
            viewModelScope.launch {
                if (data.book.id == null) {
                    repository.insert(data.book)
                } else {
                    repository.update(data.book)
                }
                _addEditUIState.update {
                    AddEditUIState.BookSaved()
                }
                _detailUIState.update {
                    DetailUIState.ScreenDataChanged(data)
                }
            }
        }
    }

    override fun bookNameChanged(name: String) {
        data.book.name = name
        _addEditUIState.update {
            AddEditUIState.ScreenDataChanged(data)
        }
    }

    override fun bookAuthorChanged(author: String) {
        data.book.author = author
        _addEditUIState.update {
            AddEditUIState.ScreenDataChanged(data)
        }
    }

    override fun bookGenreChanged(genre: String) {
        data.book.genre = genre
        _addEditUIState.update {
            AddEditUIState.ScreenDataChanged(data)
        }
    }

    override fun bookStarsChanged(stars: Int) {
        data.book.stars = stars
        _addEditUIState.update {
            AddEditUIState.ScreenDataChanged(data)
        }
    }

    override fun bookDateChanged(date: Long?) {
        data.book.date = date
        _addEditUIState.update {
            AddEditUIState.ScreenDataChanged(data)
        }
    }

    override fun deleteTask() {
        viewModelScope.launch {
            val numberOfDeleted = repository.delete(data.book)

            if (numberOfDeleted > 0) {
                _addEditUIState.update {
                    AddEditUIState.BookDeleted()
                }
            } else {

            }
        }
    }

    override fun loadBook(id: Long?) {
        if (id != null) {
            viewModelScope.launch {
                data.book = repository.getBook(id)
                _addEditUIState.update {
                    AddEditUIState.ScreenDataChanged(data)
                }
            }
        }
    }


}