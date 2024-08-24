package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.airbnb.lottie.LottieAnimationView
import com.example.myapplication.R
import com.example.myapplication.view.signIn.SignActivity

class splashScreen : AppCompatActivity() {
    lateinit var lottie: LottieAnimationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        lottie=findViewById(R.id.lottie)

      /*  Handler().postDelayed(Runnable {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },5000)*/
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, SignActivity::class.java))
            finish()
        }, 5500)

    }
}