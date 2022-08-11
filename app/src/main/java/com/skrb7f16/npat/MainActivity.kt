package com.skrb7f16.npat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btn=findViewById<Button>(R.id.home_create)
        btn.setOnClickListener {
            val intent= Intent(this,CreateGameActivity::class.java)
            startActivity(intent)
        }

        val btn2=findViewById<Button>(R.id.home_join)
        btn2.setOnClickListener{
            val intent= Intent(this,JoinGameActivity::class.java)
            startActivity(intent)
        }
    }
}