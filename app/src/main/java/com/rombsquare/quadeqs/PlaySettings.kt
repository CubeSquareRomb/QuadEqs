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
    var diff: String = "simple" // Choosed difficulty
    var time: Int = 30 // Choosed time

    // Late inits
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

        // Get difficulties and times from string resource
        val diffs = resources.getStringArray(R.array.modes)
        val times = resources.getStringArray(R.array.times)

        // Technical names for difficulties and times
        val diffs_technical_names = listOf("simple", "classic", "pro", "reduced_eq_master", "reduced_eq_master_2", "growth")
        val times_technical_names = listOf(30, 60, 120, 300, 600)

        // Adapter for difficulties
        val diff_adapter = ArrayAdapter(this, R.layout.spinner_item, diffs)
        diff_adapter.setDropDownViewResource(R.layout.spinner_menu)

        // Adapter for times
        val time_adapter = ArrayAdapter(this, R.layout.spinner_item, times)
        time_adapter.setDropDownViewResource(R.layout.spinner_menu)

        // Get components
        val diffSpin: Spinner = findViewById(R.id.diff_spin)
        val timeSpin: Spinner = findViewById(R.id.time_spin)
        val btn_custom: Button = findViewById(R.id.btn_custom)
        val btn_play: Button = findViewById(R.id.btn_next)
        highScoreText = findViewById(R.id.highscore)
        descrText = findViewById(R.id.descr)

        // Set adapters
        diffSpin.adapter = diff_adapter
        timeSpin.adapter = time_adapter

        timeSpin.setSelection(2)

        // Adapter for difficulties
        diffSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                diff = diffs_technical_names[position]
                showHighScore() // Show highscore of the choosed difficulty and time
                updateDescr() // Update description of the difficulty
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Adapter for times (similar to difficulties)
        timeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                time = times_technical_names[position]
                showHighScore()
                updateDescr()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        // Custom button: Move to the "custom" activity
        btn_custom.setOnClickListener {
            val intent = Intent(this, Custom::class.java)
            startActivity(intent)
        }

        // Play button: Start the game
        btn_play.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            intent.putExtra("diff", diff)
            intent.putExtra("time", time)
            startActivity(intent)
        }

    }

    // Get highscore by choosed difficulty and time
    private fun getHighScore(): Int {
        val sharedPrefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)

        val name = "$diff-$time"

        return sharedPrefs.getInt(name, 0)
    }

    // Show highscore
    @SuppressLint("SetTextI18n")
    fun showHighScore() {
        val highscoreText = resources.getString(R.string.highscore)
        highScoreText.text = "$highscoreText: ${getHighScore()}"
    }

    // Update description
    @SuppressLint("SetTextI18n")
    fun updateDescr() {
        val resourceName = "descr_$diff"

        val resourceId = this.resources.getIdentifier(resourceName, "string", this.packageName)

        val descr = this.getString(resourceId)

        descrText.text = descr


    }
}