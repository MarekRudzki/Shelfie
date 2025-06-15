package rudzki.marek.shelfie.home.model.dataModel

import com.google.gson.annotations.SerializedName

data class Book (
    val id: Long,
    val title: String?,
    val image: String?,
    val authors: List<Author> = emptyList(),

    @SerializedName("publish_date")
    val publishDate: Double?,

    @SerializedName("number_of_pages")
    val numberOfPages: Double?,
    val description: String?,
    val rating: Rating?,
    )


data class Author(
    val id: Long,
    val name: String?,
)

data class Rating(
    val average: Double?,
)