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

        val button: Button = findViewById(R.id.home)
        button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val checkBox: CheckBox = findViewById(R.id.simpleModeCheckBox)

        val prefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)

        isChecked = prefs.getBoolean("simple_view", true)

        checkBox.isChecked = !isChecked

        checkBox.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean("simple_view", checkBox.isChecked)
            editor.apply()
        }
    }
}