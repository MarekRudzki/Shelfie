package rudzki.marek.shelfie.home.model.network

import android.util.Log
import okhttp3.HttpUrl
import rudzki.marek.shelfie.home.model.dataModel.Book
import rudzki.marek.shelfie.home.model.dataModel.SearchBookResponse
import javax.inject.Inject

class BookNetworkRepository @Inject constructor(
    private val bookService: BookService
) {
    suspend fun getBookById(id: Long): Result<Book> {
        return try {
            val response = bookService.getBookById(id)

            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun searchBooks(
        query: String?,
        offset: Int,
        genre: String?,
    ): Result<SearchBookResponse> {
        return try {
            val requestQuery = query ?: "null"
            val genresParam = genre?.takeIf { it.isNotBlank() && it != "null" }

            Log.i("request data", "Query: $requestQuery, Offset: $offset, Genre: $genresParam")

            val minRatingParam = if (!genresParam.isNullOrBlank() && query.isNullOrBlank()) 0.1 else null
            val maxRatingParam = if (!genresParam.isNullOrBlank() && query.isNullOrBlank()) 0.99 else null

            Log.i("request data", "Query: $requestQuery, Offset: $offset, Genre: $genresParam, minRating: $minRatingParam, maxRating: $maxRatingParam")

            val response = bookService.searchBooks(
                query = if (query.isNullOrBlank()) null else query,
                offset = offset,
                genres = genresParam,
                minRating = minRatingParam,
                maxRating = maxRatingParam
            )

            Log.e("response", "Code: ${response.code()} Body: ${response.body()} ErrorBody: ${response.errorBody()?.string()}")

            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    Result.success(body)
                } else {
                    Result.failure(Exception("Empty response body"))
                }
            } else {
                Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}