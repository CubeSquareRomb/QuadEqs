package com.rombsquare.quadeqs

import IntEq
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Game : AppCompatActivity() {
    // Initialize variables for the game
    private var diff: String? = ""
    private var minX: Int? = 0
    private var maxX: Int? = 0
    private var minA: Int? = 0
    private var maxA: Int? = 0
    private var time: Int? = 0
    private var curTime: Int = 0

    // Late init for equation generator
    private lateinit var eq: IntEq

    // Late init for components
    private lateinit var eqText: TextView
    private lateinit var solvedTimeText: TextView
    private lateinit var minX_Edit: EditText
    private lateinit var maxX_Edit: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get game data
        val extras = intent.extras
        if (extras != null) {
            diff = extras.getString("diff")
            minX = extras.getInt("minX")
            maxX = extras.getInt("maxX")
            minA = extras.getInt("minA")
            maxA = extras.getInt("maxA")
            time = extras.getInt("time")

            // Chech for null
            if (minX == null) {minX = -10}
            if (maxX == null) {maxX = 10}
            if (minA == null) {minA = 1}
            if (maxA == null) {maxA = 1}

            // Get equation view parameter (simple equation or classic)
            val prefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)
            val isChecked = prefs.getBoolean("simple_view", true)

            // Initialize equation generator
            eq = IntEq(diff!!, isChecked, minX!!, maxX!!, minA!!, maxA!!)
        }

        // Get components
        val btnClose: ImageView = findViewById(R.id.btn_close)
        val btnNext: Button = findViewById(R.id.btn_next)
        eqText = findViewById(R.id.eq)
        solvedTimeText = findViewById(R.id.title)
        minX_Edit  = findViewById(R.id.minX)
        maxX_Edit  = findViewById(R.id.maxX)

        // Launch the game timer
        val timer = object : CountDownTimer(time!!.toLong()*1000, 1000) { // 10 seconds total, ticking every 1 second
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                // Get seconds for finish
                curTime = (millisUntilFinished / 1000).toInt()

                // Update texts
                updateTexts()
            }

            override fun onFinish() {
                end()
            }
        }
        timer.start()

        // Close event
        btnClose.setOnClickListener {
            timer.cancel()
            end()
        }

        // Check answer event
        btnNext.setOnClickListener {
            next()
        }
    }

    // Update texts
    @SuppressLint("SetTextI18n")
    fun updateTexts() {
        val solvedText = resources.getString(R.string.solved)
        val time = resources.getString(R.string.time)

        eqText.text = eq.text
        solvedTimeText.text = "${solvedText}: ${eq.solved} \n${time}: ${convertTime(curTime)}"
    }

    // Check answers and next level event
    fun next() {
        // Alert dialog for incorrect answer
        val builder = AlertDialog.Builder(this)
        val message = resources.getString(R.string.incorrect)
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        // If inputs are empty, show incorrect answer dialog
        if (TextUtils.isEmpty(minX_Edit.text.toString()) || TextUtils.isEmpty(maxX_Edit.text.toString())) {
            builder.create().show()
            return
        }

        // Get user roots
        val userMinX = minX_Edit.text.toString().toInt()
        val userMaxX = maxX_Edit.text.toString().toInt()

        // Clear the inputs
        minX_Edit.text.clear()
        maxX_Edit.text.clear()

        // Get state. If state is -1, roots are incorrect. Else: generate new equation
        val state = eq.next(listOf(userMinX, userMaxX))

        // If roots are incorrect
        if (state == -1) {
            builder.create().show()
            return
        }

        // Update texts if roots are correct
        updateTexts()
    }

    // End event
    fun end() {
        val sharedPrefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)

        // Name for the save
        val name = "$diff-$time"

        // If save data (solved equations) is lower than new solved equations, update the save
        if (eq.solved > sharedPrefs.getInt(name, 0)) {
            val editor = sharedPrefs.edit()
            editor.putInt(name, eq.solved)
            editor.apply()
        }

        // Move to the end screen
        val intent = Intent(this, End::class.java)
        intent.putExtra("solved", eq.solved)
        startActivity(intent)
    }

    // Convert seconds to the user friendly time (seconds -> XX:YY)
    fun convertTime(seconds: Int): String {
        val s = seconds % 60
        val m = seconds / 60

        val sText = resources.getString(R.string.s)
        val mText = resources.getString(R.string.m)

        return ("${m}${mText} ${s}${sText}")
    }

    // Back navigation button is disabled
    override fun onBackPressed() {

    }
}