package com.example.myapplication.view.signIn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.view.MainActivity
import com.example.myapplication.view.SearchActivity
import com.example.myapplication.view.favActivity
import com.google.firebase.auth.FirebaseAuth

class AccountActivity : AppCompatActivity() {
    lateinit var homebtn:Button
    lateinit var newAccount:Button
    lateinit var signOutButton: Button
    lateinit var emailtext:TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)
        var emailtext=findViewById<TextView>(R.id.accountMailView)
        var IDtext=findViewById<TextView>(R.id.IDview)
        signOutButton = findViewById(R.id.signoutbtn)
        homebtn= findViewById(R.id.btnhome)
        newAccount=findViewById(R.id.signintbn)




        var user = FirebaseAuth.getInstance().currentUser
        val userEmail = user?.email
        emailtext.text = userEmail ?: "No email available" // Handle null case

        IDtext.text=user?.uid

        signOutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            user = FirebaseAuth.getInstance().currentUser
            IDtext.text=user?.uid
            emailtext.text =  user?.email ?: "You didn't sign in or register" // Handle null case

        }
        val favbtn = findViewById<Button>(R.id.favbtn)
        favbtn.setOnClickListener({
            val outIntent = Intent(this, favActivity::class.java)
            startActivity(outIntent)
        })

        val searchbtn=findViewById<Button>(R.id.searchbtn)
        searchbtn.setOnClickListener({
            val outIntent = Intent(this, SearchActivity::class.java)
            startActivity(outIntent)
        })
        homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })
        newAccount.setOnClickListener({
            val intent=Intent(this, SignActivity::class.java)
            startActivity(intent)
        } )


    }
}