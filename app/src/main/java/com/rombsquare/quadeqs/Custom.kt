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

        // Import components
        val minX_Input: EditText = findViewById(R.id.minX)
        val maxX_Input: EditText = findViewById(R.id.maxX)
        val minA_Input: EditText = findViewById(R.id.minA)
        val maxA_Input: EditText = findViewById(R.id.maxA)
        val mTimeInput: EditText = findViewById(R.id.time_m)
        val sTimeInput: EditText = findViewById(R.id.time_s)
        val btn_play: Button = findViewById(R.id.btn_next)

        btn_play.setOnClickListener {

            // Import data from inputs
            val minX = if (minX_Input.text.toString().isEmpty()) {-10} else {minX_Input.text.toString().toInt()}
            val maxX = if (maxX_Input.text.toString().isEmpty()) {10} else {maxX_Input.text.toString().toInt()}
            val minA = if (minA_Input.text.toString().isEmpty()) {1} else {minA_Input.text.toString().toInt()}
            val maxA = if (maxA_Input.text.toString().isEmpty()) {1} else {maxA_Input.text.toString().toInt()}
            val m = if (mTimeInput.text.toString().isEmpty()) {0} else {mTimeInput.text.toString().toInt()}
            val s = if (sTimeInput.text.toString().isEmpty()) {0} else {sTimeInput.text.toString().toInt()}

            var time = m*60 + s // In seconds
            if (time <= 0) {time = 120}

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