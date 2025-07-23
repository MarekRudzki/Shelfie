package rudzki.marek.shelfie.home.view

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun SearchResultScreen(
    query: String?,
    genre: String?,
    bookViewModel: BookViewModel = hiltViewModel()
) {
    val searchBooks by bookViewModel.searchBooks.collectAsState()
    val isLoading by bookViewModel.isLoading.collectAsState()
    val error by bookViewModel.error.collectAsState()

    var loadMore by remember { mutableStateOf(false) }


    LaunchedEffect(query, genre) {
        bookViewModel.searchBooks(
            query = query,
            genre = genre,
            offset = 0
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(15.dp)
        ) {
            itemsIndexed(searchBooks) { index, book ->
                Box(
                    modifier = Modifier
                        .height(175.dp)
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .border(
                            width = 1.5.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    Row (
                        modifier = Modifier.fillMaxSize()
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(book.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Book Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(0.5f)
                                .padding(end = 25.dp)
                                .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
                        )
                        Text(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp)
                                .align(Alignment.CenterVertically),
                            text =  book.title ?: "",
                        )
                    }
                }

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
