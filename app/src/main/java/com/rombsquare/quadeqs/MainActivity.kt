package com.rombsquare.quadeqs

import IntEq
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var eq: IntEq
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Equation generator for animation
        eq = IntEq("pro")

        // Get components
        val button1: Button = findViewById(R.id.btn_next)
        val button2: Button = findViewById(R.id.btn_tut)
        val button3: Button = findViewById(R.id.btn_sett)
        val eqText: TextView = findViewById(R.id.eq)

        // If play button is clicked
        button1.setOnClickListener {
            val intent = Intent(this, PlaySettings::class.java)
            startActivity(intent)
        }

        // If tutorial button is clicked
        button2.setOnClickListener {
            val intent = Intent(this, Tutorial::class.java)
            startActivity(intent)
        }

        // If settings button is clicked
        button3.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        // Equation animation
        handler = Handler(Looper.getMainLooper())
        val eqAnim = object : Runnable {
            override fun run() {
                eq.next()
                eqText.text = eq.text
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(eqAnim)



    }

    // Stop animation after leaving from MainActivity
    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Stop the handler callbacks when the activity is destroyed
    }
}