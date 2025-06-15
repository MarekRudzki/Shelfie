package rudzki.marek.shelfie.home.model.network

import rudzki.marek.shelfie.home.model.dataModel.Book
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
}