package dev.didnt.proyecto.servicio

import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

object AppConstantes{
    const val BASE_URL = "http://:3001" //Añadir ip
}

interface WebService {
    @GET("/news")
    suspend fun obtenerNoticias(): Response<NoticiaResponse>

}

object RetrofitClient{
    val webService:WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstantes.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}