package com.example.myapplication.controller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.myapplication.view.CategoriesAdapter
import com.example.myapplication.R
import com.example.myapplication.netwrok.RetroBuilder
import com.example.myapplication.view.CategoriesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var favbtn: Button
    lateinit var btnsuggest: Button
    lateinit var accountbtn: Button
    lateinit var textView: TextView
    lateinit var swip : SwipeRefreshLayout
    //lateinit var category: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
      //  textView=findViewById(R.id.textView)


        btnsuggest = findViewById(R.id.suggestbtn)

        btnsuggest.setOnClickListener({
            val dynamicFragment = suggestMealFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.suggestFragment, dynamicFragment)
            transaction.commit()

        })


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTxt)

        lifecycleScope.launch(Dispatchers.IO) {
            try{
            val resultCategories = RetroBuilder.service.GetListCategory()
            withContext(Dispatchers.Main) {
                //textView.text = "Recived ${resultCategories.body()?.categoriesArray?.size}"
                Log.i("categories", "on response: ${resultCategories.body()}")
                val adapter = resultCategories.body()?.categoriesArray?.let { CategoriesAdapter(it,this@MainActivity) }
                recyclerView.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
                recyclerView.adapter =adapter

            }
        }
            catch (e: Exception) {
                Log.e("MainDish", "Error fetching meals: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error fetching meals: ${e.message}", Toast.LENGTH_SHORT).show()
          }
        }
        }


            favbtn = findViewById(R.id.favbtn)
            favbtn.setOnClickListener({
                val outIntent = Intent(this, favActivity::class.java)
                startActivity(outIntent)
            })
            accountbtn = findViewById(R.id.accountbtn)
            accountbtn.setOnClickListener({
                val outIntent = Intent(this, AccountActivity::class.java)
                startActivity(outIntent)
            })


        }
    }


