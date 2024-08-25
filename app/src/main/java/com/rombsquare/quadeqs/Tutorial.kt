package com.rombsquare.quadeqs

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.webkit.WebView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Tutorial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tutorial)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Create an unencoded HTML string, then convert the unencoded HTML string into
        // bytes. Encode it with base64 and load the data.

        val web: WebView = findViewById(R.id.webView)
        val btnClose: FloatingActionButton = findViewById(R.id.btn_close)

        val unencodedHtml =
                    "<html><body>\n" +
                    "<b>Beginning<br><br></b>" +
                    "Above, the equation is called <b>\"quadratic\"</b>. Your task is to find solutions to the equation. There are <b>2</b>. In this game, there will only be equations with integer solutions. There are 2 ways to solve them: the Discriminant formula and Viet's theorem. The discriminator is very accurate but slow. And Vieta is very fast for small equations, but you must find solutions by selection method. I recommend using <b>Vieta</b> so you don't use paper when playing the game.\n" +
                    "<br><br><b>Step 1. Find the numbers of the quadratic equation</b><br><br>\n" +
                    "Let's take all the numbers from this quadratic equation <b>(2x² - 10x + 12 = 0)</b>. The other part of the equation is not needed.<br><br>The first number before <b>x²</b> is the senior coefficient (2). The second number before <b>x</b> is the junior coefficient (-10). Note that this number is preceded by a minus, so it will be negative. The last number <b>without x</b> is a constant (12)\n" +
                    "Therefore, our numbers are [2, -10, 12]\n" +
                    "<br><br><b>Step 2. Make the equation reduced</b><br><br>\n" +
                    "This means that the equation will have a larger coefficient with a value of 1. For this, each number in the equation must be divided by the senior coefficient. If you already have the reduced equation, skip this step.\n" +
                    "<br><br><b>Step 3. Finding solutions</b><br><br>\n" +
                    "We know the numbers of the quadratic equation, so we can find the solutions using Vieta’s theorem:\n" +
                    "<br><br>The sum of the solutions is equal to the junior coefficient with the opposite sign\n" +
                    "<br>The product of solutions is equal to a constant\n" +
                    "<br><br><b>x1 + x2 = -b</b>\n" +
                    "<br><b>x1 * x2 = c</b>\n" +
                    "<br><br>Next, it is necessary to find such x1 and x2 that they satisfy these 2 expressions shown above. Here you need to use selection method\n" +
                    "<br><br><b>Example</b><br><br>\n" +
                    "We know the numbers of this quadratic equation: [2, -10, 12]. It can be seen that it is not reduced because the senior coefficient is not 1. You need to divide the numbers by 2 and you will get: [1, -5, 6]. Next, we use Viet's theorem:\n" +
                    "<br><br><b>x1 + x2 = 5</b>\n" +
                    "<br><b>x1 * x2 = 6</b>\n" +
                    "<br><br>After using selection method, we got solutions:\n" +
                    "<br><br><b>x1 = 2<b/>\n" +
                    "<br><b>x2 = 3</b>\n" +
                    "<br><br><br>" +
                    "</body>\n" +
                    "</html>"

        btnClose.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val encodedHtml = Base64.encodeToString(unencodedHtml.toByteArray(), Base64.NO_PADDING)
        web.loadData(encodedHtml, "text/html", "base64")
    }
}