package com.rombsquare.quadeqs

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Settings : AppCompatActivity() {
    var isChecked: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get components
        val button: Button = findViewById(R.id.home)
        val checkBox: CheckBox = findViewById(R.id.simpleModeCheckBox)

        // Home button listener
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val prefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)

        // Get "is simple view of equation?" parameter
        isChecked = prefs.getBoolean("simple_view", true)
        checkBox.isChecked = !isChecked

        // If checkbox is clicked, save simple view parameter to device
        checkBox.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean("simple_view", !checkBox.isChecked)
            editor.apply()
        }
    }
}