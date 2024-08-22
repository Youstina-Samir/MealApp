package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class SignActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign)
      /*  mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        Handler().postDelayed({
        if (currentUser != null) {
            val Mainintent = Intent(this@SignActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            val Loginintent = Intent(this@SignActivity, AccountActivity::class.java)
            startActivity(intent)
        }
            },2000)*/
       val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
           .requestIdToken(getString(R.string.default_web_client_id))
           .requestEmail()
           .build()
        googleSignInClient= GoogleSignIn.getClient(this, gso)
        mAuth = FirebaseAuth.getInstance()

    }
}