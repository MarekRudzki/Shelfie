package rudzki.marek.shelfie.home.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import rudzki.marek.shelfie.home.model.dataModel.Genre
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesGrid(
    onCategorySelected: (String) -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val rawGenres = listOf(
        "action",
        "adventure",
        "anthropology",
        "astronomy",
        "archaeology",
        "art",
        "biography",
        "biology",
        "business",
        "chemistry",
        "classics",
        "contemporary",
        "cookbook",
        "crafts",
        "crime",
        "dystopia",
        "economics",
        "education",
        "engineering",
        "environment",
        "erotica",
        "essay",
        "fantasy",
        "fashion",
        "feminism",
        "fiction",
        "finance",
        "folklore",
        "food",
        "gardening",
        "geology",
        "health",
        "historical",
        "history",
        "horror",
        "humor",
        "journalism",
        "law",
        "literature",
        "manga",
        "mathematics",
        "medieval",
        "memoir",
        "mystery",
        "mythology",
        "nature",
        "nonfiction",
        "novel",
        "occult",
        "paranormal",
        "parenting",
        "philosophy",
        "physics",
        "poetry",
        "politics",
        "programming",
        "psychology",
        "reference",
        "relationships",
        "religion",
        "romance",
        "society",
        "sociology",
        "space",
        "spirituality",
        "sports",
        "thriller",
        "travel",
        "war",
        "writing",
    )

    val bookGenres = rawGenres.map {
        raw ->
        Genre(
            value = raw,
            label = raw.replace("_", " ")
                .split(" ")
                .joinToString(" ") {
                    it.replaceFirstChar {
                        c -> c.uppercaseChar()
                    }
                }
        )
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(top = 5.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(bookGenres) {
            genre ->
    Box(
        modifier = Modifier
            .padding(10.dp)
            .width(screenWidth * 0.42f)
            .height(screenWidth * 0.3f)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                ),
                shape = RoundedCornerShape(10.dp),
                )
            .background(
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable {
                onCategorySelected(genre.label)
            }
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = genre.label,
            textAlign = TextAlign.Center,
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.W700,
                fontSize = 20.sp,
            )
        )
    }
    }
    }
}