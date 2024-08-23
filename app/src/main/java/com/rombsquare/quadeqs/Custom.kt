package com.rombsquare.quadeqs

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Custom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_custom)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val minX_Input: EditText = findViewById(R.id.minX)
        val maxX_Input: EditText = findViewById(R.id.maxX)
        val minA_Input: EditText = findViewById(R.id.minA)
        val maxA_Input: EditText = findViewById(R.id.maxA)
        val timeInput: EditText = findViewById(R.id.time)


        val btn_play: Button = findViewById(R.id.btn_next)
        btn_play.setOnClickListener {
            val minX = minX_Input.text.toString().toIntOrNull()
            val maxX = maxX_Input.text.toString().toIntOrNull()
            val minA = minA_Input.text.toString().toIntOrNull()
            val maxA = maxA_Input.text.toString().toIntOrNull()
            val time = timeInput.text.toString().toIntOrNull()

            val intent = Intent(this, Game::class.java)

            intent.putExtra("diff", "custom")
            intent.putExtra("minX", minX)
            intent.putExtra("maxX", maxX)
            intent.putExtra("minA", minA)
            intent.putExtra("maxA", maxA)
            intent.putExtra("time", time)

            startActivity(intent)
        }
    }
}