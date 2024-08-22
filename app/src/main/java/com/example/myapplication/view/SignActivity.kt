package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySignBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class SignActivity : AppCompatActivity() {
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var googleSignInClient : GoogleSignInClient
    private lateinit var binding: ActivitySignBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()
        binding.signbtn.setOnClickListener {
            val email=binding.SignEmail.text.toString()
            val password=binding.editTextTextPassword.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) {task ->
                    if(task.isSuccessful){
                            Toast.makeText(this@SignActivity,"Sign Up Successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SignActivity, LoginActivity::class.java)
                            startActivity(intent)
                            finish()

                        }
                        else
                            Toast.makeText(this@SignActivity,"Sign Up Failed", Toast.LENGTH_SHORT).show()
                    }
            }else
                Toast.makeText(this@SignActivity,"Enter email and password", Toast.LENGTH_SHORT).show()

        }
        binding.LoginText.setOnClickListener({
            val intent = Intent(this@SignActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })



        }
}