package rudzki.marek.shelfie.home.view.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import rudzki.marek.shelfie.home.model.dataModel.SearchBook

@Composable
fun BookTile(
    navController: NavController,
    book: SearchBook
) {
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
            .clickable(
                onClick = {
                    navController.navigate("book_details?bookId=${book.id}")
                }
            )
    ) {
        Row(
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
                    .fillMaxWidth(0.45f)
                    .clip(RoundedCornerShape(topStart = 10.dp, bottomStart = 10.dp))
            )
            Column(
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 15.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 5.dp),
                    text = if (book.authors.isNotEmpty()) book.authors.first().name
                        ?: "" else "",
                    style = TextStyle(
                        fontWeight = FontWeight.W800,
                        fontSize = 19.sp,
                        color = Color.Gray
                    ),
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    text = book.title ?: "",
                    style = TextStyle(
                        fontWeight = FontWeight.W600,
                        fontSize = 17.sp
                    ),
                    textAlign = TextAlign.Center,
                    overflow = TextOverflow.Ellipsis,
                )
                Box {}
            }
        }
    }
}