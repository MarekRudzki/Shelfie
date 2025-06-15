package rudzki.marek.shelfie.home.model.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import rudzki.marek.shelfie.home.model.dataModel.Book

interface BookService {
    @GET("{id}")
    suspend fun getBookById(
        @Path("id") id: Long
    ) : Response<Book>
}