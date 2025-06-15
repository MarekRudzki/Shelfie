package rudzki.marek.shelfie.home.model.network

import android.util.Log
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

    suspend fun searchBook(
        query: String?,
        offset: Int,
        genre: String?,
    ): Result<SearchBookResponse> {
        return try {
            val response = bookService.searchBooks(
                query = query ?: "null",
                offset = offset,
                genres = genre
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