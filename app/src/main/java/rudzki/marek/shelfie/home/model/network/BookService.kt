package rudzki.marek.shelfie.home.model.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rudzki.marek.shelfie.home.model.dataModel.Book
import rudzki.marek.shelfie.home.model.dataModel.SearchBookResponse

interface BookService {
    @GET("{id}")
    suspend fun getBookById(
        @Path("id") id: Long
    ) : Response<Book>


    @GET("search-books")
    suspend fun searchBooks(
        @Query("query") query: String? = null,
        @Query("offset") offset: Int,
        @Query("number") number: Int = 30,
        @Query("genres") genres: String? = null,
        @Query("sort") sort: String? = "rating",
        // Min and max rating to exclude books with only 1 review or 0 reviews.
        // Default null - API returns 500 when using min/max rating with query. Works only with genres.
        @Query("min-rating") minRating: Double? = null,
        @Query("max-rating") maxRating: Double? = null
    ) : Response<SearchBookResponse>
}