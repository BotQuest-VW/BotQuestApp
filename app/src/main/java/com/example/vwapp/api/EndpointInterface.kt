package com.example.vwapp.api
import com.example.vwapp.models.Login
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface EndpointInterface {
    @POST("login")
    fun login(@Body usuario: Login): Call<JsonObject>

    @GET("usuario/{idUsuario}")
    fun buscarUsuarioPorID(@Path(value = "idUsuario", encoded = true) id: UUID) : Call<JsonObject>


    @Multipart
    @PUT("usuario/editar-imagem/{idUsuario}")
    fun editarImagemUsuario(
        @Part imagem: MultipartBody.Part,
        @Path(value = "idUsuario", encoded = true) id: UUID
    ) : Call<JsonObject>
}
