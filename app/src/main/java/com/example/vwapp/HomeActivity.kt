package com.example.vwapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val iconeUser = findViewById<ImageView>(R.id.imageView4)
        iconeUser.setOnClickListener(){
            val intentPerfil = Intent(this@HomeActivity, PerfilActivity::class.java)

            startActivity(intentPerfil)
        }

    }


}