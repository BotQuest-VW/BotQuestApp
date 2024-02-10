package com.example.vwapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView

import androidx.appcompat.app.AppCompatActivity

class PerfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user)

        val iconeHome = findViewById<ImageView>(R.id.imageView7)
        iconeHome.setOnClickListener(){
            val returnHome = Intent(this@PerfilActivity, HomeActivity::class.java)

            startActivity(returnHome)
        }
    }
}

