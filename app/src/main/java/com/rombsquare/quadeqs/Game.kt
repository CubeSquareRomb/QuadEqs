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
    private var diff: String? = ""
    private var minX: Int? = 0
    private var maxX: Int? = 0
    private var minA: Int? = 0
    private var maxA: Int? = 0
    private var time: Int? = 0

    private lateinit var eq: IntEq
    private var curTime: Int = 0

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

        val extras = intent.extras
        if (extras != null) {
            diff = extras.getString("diff")
            minX = extras.getInt("minX")
            maxX = extras.getInt("maxX")
            minA = extras.getInt("minA")
            maxA = extras.getInt("maxA")
            time = extras.getInt("time")

            if (minX == null) {minX = -10}
            if (maxX == null) {maxX = 10}
            if (minA == null) {minA = 1}
            if (maxA == null) {maxA = 1}

            val prefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)
            val isChecked = prefs.getBoolean("simple_view", true)

            eq = IntEq(diff!!, isChecked, minX!!, maxX!!, minA!!, maxA!!)
        }

        val btnClose: ImageView = findViewById(R.id.btn_close)
        val btnNext: Button = findViewById(R.id.btn_next)
        eqText = findViewById(R.id.eq)
        solvedTimeText = findViewById(R.id.title)
        minX_Edit  = findViewById(R.id.minX)
        maxX_Edit  = findViewById(R.id.maxX)

        val timer = object : CountDownTimer(time!!.toLong()*1000, 1000) { // 10 seconds total, ticking every 1 second
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                curTime = (millisUntilFinished / 1000).toInt()
                updateTexts()
            }

            override fun onFinish() {
                end()
            }
        }

        timer.start()

        btnClose.setOnClickListener {
            timer.cancel()
            end()
        }

        btnNext.setOnClickListener {
            next()
        }
    }

    @SuppressLint("SetTextI18n")
    fun updateTexts() {
        val solvedText = resources.getString(R.string.solved)
        val time = resources.getString(R.string.time)
        eqText.text = eq.text

        solvedTimeText.text = "${solvedText}: ${eq.solved} \n${time}: ${convertTime(curTime)}"
    }

    fun next() {
        val builder = AlertDialog.Builder(this)

        val message = resources.getString(R.string.incorrect)
        builder.setMessage(message)

        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }

        if (TextUtils.isEmpty(minX_Edit.text.toString()) || TextUtils.isEmpty(maxX_Edit.text.toString())) {
            builder.create().show()
            return
        }

        val userMinX = minX_Edit.text.toString().toInt()
        val userMaxX = maxX_Edit.text.toString().toInt()

        minX_Edit.text.clear()
        maxX_Edit.text.clear()

        val state = eq.next(listOf(userMinX, userMaxX))

        if (state == -1) {
            builder.create().show()
            return
        }

        updateTexts()
    }

    fun end() {
        val sharedPrefs = getSharedPreferences("QuadEqs", MODE_PRIVATE)

        val name = "$diff-$time"

        if (eq.solved > sharedPrefs.getInt(name, 0)) {
            val editor = sharedPrefs.edit()
            editor.putInt(name, eq.solved)
            editor.apply()
        }

        val intent = Intent(this, End::class.java)
        intent.putExtra("solved", eq.solved)
        startActivity(intent)
    }

    fun convertTime(seconds: Int): String {
        val s = seconds % 60
        val m = seconds / 60

        val sText = resources.getString(R.string.s)
        val mText = resources.getString(R.string.m)

        return ("${m}${mText} ${s}${sText}")
    }

    override fun onBackPressed() {

    }
}