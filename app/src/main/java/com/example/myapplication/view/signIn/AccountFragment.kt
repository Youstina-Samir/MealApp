package com.example.myapplication.view.signIn

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.google.firebase.auth.FirebaseAuth


class AccountFragment : Fragment() {
    lateinit var homebtn: Button
    lateinit var newAccount: Button
    lateinit var signOutButton: Button
    lateinit var emailtext: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        var emailtext=view.findViewById<TextView>(R.id.accountMailView)
        var IDtext=view.findViewById<TextView>(R.id.IDview)
        var imageView=view.findViewById<ImageView>(R.id.imageView2)
        signOutButton = view.findViewById(R.id.signoutbtn)
        newAccount=view.findViewById(R.id.signintbn)




        var user = FirebaseAuth.getInstance().currentUser
        val userEmail = user?.email

        Glide.with(this)
            .load( user?.photoUrl)
            .into(imageView)
        emailtext.text = userEmail ?: "No email available" // Handle null case

        IDtext.text=user?.uid

        signOutButton.setOnClickListener {
            if( user != null) {
                FirebaseAuth.getInstance().signOut()
                user = FirebaseAuth.getInstance().currentUser
                IDtext.text = user?.uid
                emailtext.text = user?.email ?: "You didn't sign in or register"
            } else { Toast.makeText(this@AccountFragment.requireContext(), "You didn't sign in or register", Toast.LENGTH_SHORT).show()}

        }

        newAccount.setOnClickListener({
            val intent= Intent(this@AccountFragment.requireContext(), SignActivity::class.java)
            startActivity(intent)
        } )



        return view
    }


}