package com.rombsquare.quadeqs

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlaySettings : AppCompatActivity() {
    var diff: String = String()
    var time: Int = 0

    private lateinit var highScoreText: TextView
    private lateinit var descrText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_play_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val diffs = listOf("Simple", "Classic", "Advanced", "Pro", "Growth")
        val times = listOf("30s", "1m", "2m", "5m", "10m")

        val diff_adapter = ArrayAdapter(this, R.layout.spinner_item, diffs)
        diff_adapter.setDropDownViewResource(R.layout.spinner_menu)

        val time_adapter = ArrayAdapter(this, R.layout.spinner_item, times)
        time_adapter.setDropDownViewResource(R.layout.spinner_menu)

        val diffSpin: Spinner = findViewById(R.id.diff_spin)
        val timeSpin: Spinner = findViewById(R.id.time_spin)
        highScoreText = findViewById(R.id.highscore)
        descrText = findViewById(R.id.descr)

        diffSpin.adapter = diff_adapter
        timeSpin.adapter = time_adapter

        diffSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                diff = convertDiff(diffSpin.selectedItem.toString())
                time = convertTime(timeSpin.selectedItem.toString())
                showHighScore()
                updateDescr()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        timeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                diff = convertDiff(diffSpin.selectedItem.toString())
                time = convertTime(timeSpin.selectedItem.toString())
                showHighScore()
                updateDescr()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        val btn_custom: Button = findViewById(R.id.btn_custom)
        btn_custom.setOnClickListener {
            val intent = Intent(this, Custom::class.java)

            startActivity(intent)
        }

        val btn_play: Button = findViewById(R.id.btn_next)
        btn_play.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("diff", diff)
            intent.putExtra("time", time)
            startActivity(intent)
        }

    }

    fun convertDiff(diff: String): String {
        return when (diff) {
            "Simple" -> "simple"
            "Classic" -> "classic"
            "Advanced" -> "advanced"
            "Pro" -> "pro"
            "Growth" -> "growth"
            else -> "classic"
        }
    }

    fun convertTime(time: String): Int {
        return when (time) {
            "30s" -> {30}
            "1m" -> {60}
            "2m" -> {120}
            "5m" -> {300}
            "10m" -> {600}

            else -> { time.toInt() }
        }
    }

    fun getHighScore(): Int {
        val sharedPrefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)

        val name = "$diff-$time"

        return sharedPrefs.getInt(name, 0)
    }

    @SuppressLint("SetTextI18n")
    fun showHighScore() {
        highScoreText.text = "Highscore: ${getHighScore()}"
    }

    @SuppressLint("SetTextI18n")
    fun updateDescr() {
        val resourceName = "descr_$diff"

        val resourceId = this.resources.getIdentifier(resourceName, "string", this.packageName)

        val descr = if (resourceId != 0) {
            this.getString(resourceId)
        } else {
            "<empty>"
        }

        descrText.text = descr


    }
}