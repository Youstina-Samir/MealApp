package com.example.myapplication.controller

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

class AccountActivity : AppCompatActivity() {
    lateinit var homebtn:Button
    lateinit var signupbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        homebtn= findViewById(R.id.btnhome)
        homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })
        /*
        signupbtn=findViewById(R.id.signupbtn)
        signupbtn.setOnClickListener({
            val dynamicFragment = LoginFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.loginFragmentContainerView, dynamicFragment)
            transaction.commit()

        })*/
    }
}