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

        eq = IntEq("advanced_classic")

        val button: Button = findViewById(R.id.btn_next)
        val eqText: TextView = findViewById(R.id.eq)
        button.setOnClickListener {
            val intent = Intent(this, PlaySettings::class.java)
            startActivity(intent)
        }

        handler = Handler(Looper.getMainLooper())
        var i = 0

        val eqAnim = object : Runnable {
            override fun run() {
                eq.next()
                eqText.text = eq.text
//                Toast.makeText(this@MainActivity, eq.text, Toast.LENGTH_LONG).show()
                handler.postDelayed(this, 1000)
            }
        }

        handler.post(eqAnim)



    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // Stop the handler callbacks when the activity is destroyed
    }
}