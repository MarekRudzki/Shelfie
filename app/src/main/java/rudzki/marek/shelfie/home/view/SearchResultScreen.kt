package rudzki.marek.shelfie.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import rudzki.marek.shelfie.home.viewModel.BookViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import rudzki.marek.shelfie.home.view.components.BookTile
import rudzki.marek.shelfie.home.view.components.CustomAppBar

@Composable
fun SearchResultScreen(
    query: String?,
    genre: String?,
    navController: NavController,
    bookViewModel: BookViewModel = hiltViewModel()
) {
    val searchBooks by bookViewModel.searchBooks.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()
    val error by bookViewModel.error.collectAsState()

    var loadMore by remember { mutableStateOf(false) }

    val safeGenre = genre?.takeIf { it.isNotBlank() && it != "null" }

    LaunchedEffect(query, genre) {
        bookViewModel.searchBooks(
            query = query,
            genre = safeGenre,
            offset = 0
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            CustomAppBar(
                navController = navController,
                title = "Search Results"
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = 15.dp,
                    end = 15.dp,
                    bottom = 15.dp,
                )
            ) {
                itemsIndexed(searchBooks) { index, book ->
                    BookTile(
                        navController = navController,
                        book = book,
                    )

                    if (index == searchBooks.lastIndex && !isLoading && !loadMore) {
                        loadMore = true
                        LaunchedEffect(Unit) {
                            bookViewModel.searchBooks(
                                query = query,
                                genre = genre,
                                offset = 0
                            )
                        }
                    }
                }

                if (loadMore) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }

        if (isLoading && searchBooks.isEmpty()) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }

        error?.let {
            Text(
                text = it ,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}
