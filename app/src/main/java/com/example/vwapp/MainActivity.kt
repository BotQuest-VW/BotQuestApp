package com.example.vwapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.vwapp.api.EndpointInterface
import com.example.vwapp.api.RetrofitConfig
import com.example.vwapp.models.Login
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.StandardCharsets
import java.util.*

class MainActivity : AppCompatActivity() {

    private val clienteRetrofit = RetrofitConfig.obterInstanciaRetrofit()

    private val endpoints = clienteRetrofit.create(EndpointInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val iconeUser = findViewById<Button>(R.id.login)
        iconeUser.setOnClickListener(){
//            val intentPerfil = Intent(this@MainActivity, HomeActivity::class.java)
//
//            startActivity(intentPerfil)
            autenticarUsuario()

        }

    }
    private fun autenticarUsuario() {

        val idEmail = findViewById<EditText>(R.id.editTextNumber)
        val idSenha = findViewById<EditText>(R.id.editTextNumber3)

        val emailDigitado = idEmail.text.toString()
        val senhaDigitado = idSenha.text.toString()

        val usuario = Login(emailDigitado, senhaDigitado)

        endpoints.login(usuario).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                when(response.code()){
                    200 -> {
                        val idUsuario = decodificarToken(response.body().toString())

                        val sharedPreferences = getSharedPreferences("id", Context.MODE_PRIVATE)

                        val editor = sharedPreferences.edit()

                        editor.putString("id", idUsuario.toString())

                        editor.apply()

                        //direcionando o usuário para tela lista de serviços
                        val mainIntent = Intent(this@MainActivity, HomeActivity::class.java)

                        startActivity(mainIntent)

                        finish()
                    }
                    403 -> { tratarFalhaNaAutenticacao("Email e/ou senha inválidos.")}
                    else -> {tratarFalhaNaAutenticacao("Falha ao se logar!")}
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                tratarFalhaNaAutenticacao("Falha ao tentar se logar!")
            }

        })
    }


    private fun tratarFalhaNaAutenticacao(mensagemErro: String){
        Toast.makeText(this, mensagemErro, Toast.LENGTH_SHORT).show()
    }

    private fun decodificarToken(token: String): Any {
        val partes = token.split(".")
        val payloadBase64 = partes[1]

        val payloadBytes = Base64.getUrlDecoder().decode(payloadBase64)
        val payloadJson = String(payloadBytes, StandardCharsets.UTF_8)

        val json = JSONObject(payloadJson)
        return json["idUsuario"].toString()
    }


}