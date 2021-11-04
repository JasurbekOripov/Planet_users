package uz.juo.planetusers.retrofit

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.juo.planetusers.models.User

interface ApiService {
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int): Response<User>
}