package rudzki.marek.shelfie.home.viewModel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import rudzki.marek.shelfie.home.model.dataModel.Book
import rudzki.marek.shelfie.home.model.dataModel.SearchBook
import rudzki.marek.shelfie.home.model.network.BookNetworkRepository
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val repository: BookNetworkRepository
): ViewModel() {
    private val _book = MutableStateFlow<Book?>(null)
    val book: StateFlow<Book?> = _book

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _searchBooks = MutableStateFlow<List<SearchBook>>(emptyList())
    val searchBooks: StateFlow<List<SearchBook>> = _searchBooks

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var _isBookLoading = MutableStateFlow(false)
    val isBookLoading: StateFlow<Boolean> = _isBookLoading

    fun fetchBook(id: Long) {
        if (_isBookLoading.value) return
        _isBookLoading.value = true

        viewModelScope.launch {
            val result = repository.getBookById(id)

            result
                .onSuccess { _book.value = it }
                .onFailure { _error.value = it.message }

            _isBookLoading.value = false
        }
    }

    fun searchBooks(
        query: String? = null,
        offset: Int,
        genre: String? = null,
        isLoadMore: Boolean = false,
    ) {
        if (_isLoading.value) return
        _isLoading.value = true

        viewModelScope.launch {
            val result = repository.searchBooks(
                query = query,
                offset = offset,
                genre = genre
            )

            result
                .onSuccess { response ->
                    val flatList = response.books.flatten()
                    if (isLoadMore) {
                        _searchBooks.value = _searchBooks.value + flatList
                    } else {
                        _searchBooks.value = flatList
                    }
                }
                .onFailure { _error.value = it.message }

            _isLoading.value = false
        }
    }
}