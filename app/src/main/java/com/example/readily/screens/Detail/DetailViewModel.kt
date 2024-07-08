package com.example.readily.screens.Detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.readily.database.ILocalBooksRepository
import com.example.readily.screens.ScreenData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: ILocalBooksRepository) : ViewModel(), DetailActions {

    private val data: ScreenData = ScreenData()

    private val _detailUIState: MutableStateFlow<DetailUIState> = MutableStateFlow(value = DetailUIState.Loading())

    val detailUIState = _detailUIState.asStateFlow()

    override fun deleteBook() {
        viewModelScope.launch {
            val numberOfDeleted = repository.delete(data.book)

            if (numberOfDeleted > 0) {
                _detailUIState.update {
                    DetailUIState.BookDeleted()
                }
            } else {

            }
        }
    }

     override fun loadBook(id: Long?) {
        if (id != null) {
            viewModelScope.launch {
                data.book = repository.getBook(id)
                _detailUIState.update {
                    DetailUIState.ScreenDataChanged(data)
                }
            }
        }
    }


}
