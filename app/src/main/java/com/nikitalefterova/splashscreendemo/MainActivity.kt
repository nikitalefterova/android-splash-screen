package com.nikitalefterova.splashscreendemo

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var isDataLoaded = false
    private lateinit var progressBar: ProgressBar
    private lateinit var statusText: TextView
    private lateinit var btnReload: Button
    private lateinit var infoText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        try {
            setupSplashScreen(splashScreen)

            Log.d("SplashScreen", "Splash screen successfully installed")
        } catch (e: Exception) {
            Log.e("SplashScreen", "Error installing splash screen: ${e.message}", e)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

        loadData()
    }

    private fun setupSplashScreen(splashScreen: androidx.core.splashscreen.SplashScreen) {
        splashScreen.setKeepOnScreenCondition { !isDataLoaded }

        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val iconView = splashScreenView.iconView
            val splashView = splashScreenView.view

            // Ikona pada navzdol
            val fall = ObjectAnimator.ofFloat(
                iconView,
                View.TRANSLATION_Y,
                0f,
                splashView.height.toFloat()
            )
            fall.duration = 1000
            fall.interpolator = AccelerateDecelerateInterpolator()

            // Rotacija med padanjem
            val rotate = ObjectAnimator.ofFloat(iconView, View.ROTATION, 0f, 180f)
            rotate.duration = 1000

            // Alpha fade
            val alpha = ObjectAnimator.ofFloat(iconView, View.ALPHA, 1f, 0f)
            alpha.duration = 500
            alpha.startDelay = 500

            // Background slide
            val bgSlide = ObjectAnimator.ofFloat(
                splashView,
                View.TRANSLATION_Y,
                0f,
                splashView.height.toFloat()
            )
            bgSlide.duration = 1200
            bgSlide.startDelay = 200

            val animSet = AnimatorSet()
            animSet.playTogether(fall, rotate, alpha, bgSlide)
            animSet.doOnEnd { splashScreenView.remove() }
            animSet.start()
        }
    }

    private fun initializeViews() {
        progressBar = findViewById(R.id.progressBar)
        statusText = findViewById(R.id.statusText)
        btnReload = findViewById(R.id.btnReload)
        infoText = findViewById(R.id.infoText)

        btnReload.setOnClickListener {
            reloadData()
        }
    }

    private fun loadData() {
        try {
            lifecycleScope.launch {
                updateStatus("Nalaganje podatkov...")
                progressBar.visibility = View.VISIBLE

                // Simulacija nalaganja (2 sekunde)
                delay(2000)

                isDataLoaded = true
                updateStatus("Podatki naloženi uspešno! ✓")
                progressBar.visibility = View.GONE

                displayInfo()

                Log.d("SplashScreen", "Data loaded successfully")
            }
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun reloadData() {
        isDataLoaded = false
        btnReload.isEnabled = false
        infoText.text = ""

        try {
            lifecycleScope.launch {
                updateStatus("Ponovno nalaganje...")
                progressBar.visibility = View.VISIBLE

                // Simulacija nalaganja(1.5sekunde)
                delay(1500)

                isDataLoaded = true
                updateStatus("Podatki ponovno naloženi!")
                progressBar.visibility = View.GONE
                btnReload.isEnabled = true

                displayInfo()
            }
        } catch (e: Exception) {
            handleError(e)
        }
    }

    private fun updateStatus(message: String) {
        statusText.text = message
    }

    private fun displayInfo() {
        val info = """
             Android Splash Screen API Demo
            
             Prikazani primeri:
            
            1. Osnovni Splash Screen
               - Uporabljena ikona aplikacije
               - Barva ozadja: #6200EE
            
            2. Splash Screen s pogojem
               - Ostal viden dokler se niso naložili podatki
               - Trajanje: ~2 sekundi
            
            3. Custom animacija izhoda
               - Fade out effect (500ms)
               - Smooth prehod
            
             Tehnične informacije:
            - API Level: 21+
            - Knjižnica: androidx.core:core-splashscreen
            - Licenca: Apache 2.0
            - Časovna zahtevnost: O(1)
            - Prostorska zahtevnost: O(1)
        """.trimIndent()

        infoText.text = info
    }

    private fun handleError(exception: Exception) {
        Log.e("SplashScreen", "Error: ${exception.message}", exception)
        updateStatus(" Napaka: ${exception.message}")
        progressBar.visibility = View.GONE
        btnReload.isEnabled = true

        infoText.text = """
            Prišlo je do napake pri nalaganju podatkov.
            
            Možne izjeme:
            1. NetworkException - težave z omrežjem
            2. TimeoutException - timeout pri nalaganju
            3. DataParseException - napaka pri parsanju podatkov
            
            Pritisnite "Ponovno naloži" za ponovni poskus.
        """.trimIndent()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("SplashScreen", "Activity destroyed")
    }
}
