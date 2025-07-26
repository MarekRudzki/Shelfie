package rudzki.marek.shelfie.home.model.dataModel

data class SearchBook(
    val id: Long,
    val title: String?,
    val image: String?,
    val authors: List<Author> = emptyList(),
)
