package rudzki.marek.shelfie.home.view

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import rudzki.marek.shelfie.home.viewModel.BookViewModel

//bookViewModel: BookViewModel = hiltViewModel(),


//    val book by bookViewModel.book.collectAsState()
//    val error by bookViewModel.error.collectAsState()
//
//    val searchBooks by bookViewModel.searchBooks.collectAsState()

@Composable
fun BookDetailsScreen(
    bookViewModel: BookViewModel = hiltViewModel()
) {

}