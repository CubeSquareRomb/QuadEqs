package com.rombsquare.quadeqs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class End : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get components
        val btnHome: Button = findViewById(R.id.home)
        val statsText: TextView = findViewById(R.id.stats)

        // Get the count of solved equations
        val solved = intent.extras!!.getInt("solved").toString()
        val solvedText = resources.getString(R.string.solved_eqs)

        // Show stats
        statsText.text = "$solvedText: $solved"

        // To main menu:
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    // Back navigation button is disabled
    override fun onBackPressed() {

    }
}