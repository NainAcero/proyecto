package com.nain.ejercicio.io

import com.nain.ejercicio.models.Persona
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("personas")
    @Headers("Accept: application/json")
    fun getPersona(): Call<ArrayList<Persona>>

    @GET("personas/find/{nombre}")
    @Headers("Accept: application/json")
    fun getPersonaFindNombre(
        @Path("nombre") nombre: String?
    ): Call<ArrayList<Persona>>

    companion object Factory {
        private const val BASE_URL = "http://192.168.0.13:8080/api/"

        fun create(): ApiService {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }

}