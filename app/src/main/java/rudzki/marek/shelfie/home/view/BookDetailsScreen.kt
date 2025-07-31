package rudzki.marek.shelfie.home.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import rudzki.marek.shelfie.home.viewModel.BookViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material3.Icon
import androidx.navigation.NavController
import rudzki.marek.shelfie.home.view.components.CustomAppBar

@Composable
fun BookDetailsScreen(
    bookId: Long,
    navController: NavController,
    bookViewModel: BookViewModel = hiltViewModel(),
) {
    val book by bookViewModel.book.collectAsState()
    val error by bookViewModel.error.collectAsState()
    val isBookLoading by bookViewModel.isBookLoading.collectAsState()
    val scrollState = rememberScrollState()

    LaunchedEffect(bookId) {
        bookViewModel.fetchBook(bookId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(WindowInsets.navigationBars.asPaddingValues())
    ) {
        Column {
            CustomAppBar(
                navController = navController,
                title = "Book Details"
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(scrollState),
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight(0.5f)
                            .fillMaxWidth(0.5f)
                            .shadow(
                                elevation = 8.dp,
                                shape = RoundedCornerShape(bottomEnd = 15.dp),
                                ambientColor = Color.Black.copy(alpha = 0.3f),
                                spotColor = Color.Black.copy(alpha = 0.4f)
                            )
                            .clip(RoundedCornerShape(bottomEnd = 15.dp))
                    ) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(book?.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = "Book Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = book?.title ?: "",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Author: ${book?.authors?.firstOrNull()?.name ?: "Unknown"}",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (book?.publishDate != null)
                            Text(
                                text = "Publish date: ${book?.publishDate?.toInt()}",
                                fontSize = 16.sp
                            )
                        Spacer(modifier = Modifier.height(16.dp))

                        val rating = book?.rating?.average ?: 0.0
                        val starsOutOf5 = (rating * 100) / 20.0

                        val fullStars = starsOutOf5.toInt()
                        val hasHalfStar = (starsOutOf5 - fullStars) > 0.5

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { index ->
                                Icon(
                                    imageVector = when {
                                        index < fullStars -> Icons.Default.Star
                                        index == fullStars && hasHalfStar -> Icons.Default.StarHalf
                                        else -> Icons.Default.StarBorder
                                    },
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "${String.format("%.1f", starsOutOf5)} / 5",
                                fontSize = 14.sp
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = book?.description ?: "",
                    fontSize = 15.sp
                )

                if (isBookLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Start))
                }

                error?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(16.dp)
                    )
                }
            }
        }
    }

}