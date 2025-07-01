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
fun CategoriesGrid() {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val rawGenres = listOf(
        "action",
        "adventure",
        "anthropology",
        "astronomy",
        "archaeology",
        "architecture",
        "art",
        "aviation",
        "biography",
        "biology",
        "business",
        "chemistry",
        "children",
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
        "fairy_tales",
        "fantasy",
        "fashion",
        "feminism",
        "fiction",
        "finance",
        "folklore",
        "food",
        "gaming",
        "gardening",
        "geography",
        "geology",
        "graphic_novel",
        "health",
        "historical",
        "historical_fiction",
        "history",
        "horror",
        "how_to",
        "humor",
        "inspirational",
        "journalism",
        "law",
        "literary_fiction",
        "literature",
        "magical_realism",
        "manga",
        "martial_arts",
        "mathematics",
        "medicine",
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
        "picture_book",
        "poetry",
        "politics",
        "programming",
        "psychology",
        "reference",
        "relationships",
        "religion",
        "romance",
        "science_and_technology",
        "science_fiction",
        "self_help",
        "short_stories",
        "society",
        "sociology",
        "space",
        "spirituality",
        "sports",
        "text_book",
        "thriller",
        "travel",
        "true_crime",
        "war",
        "writing",
        "young_adult"
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
        modifier = Modifier.padding(vertical = 10.dp),
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
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(10.dp),
            )
            .clickable {

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