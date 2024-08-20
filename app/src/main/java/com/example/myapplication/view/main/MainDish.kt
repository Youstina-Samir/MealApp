package com.example.myapplication.view.main

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
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.Meals
import com.example.myapplication.R
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.view.AccountActivity
import com.example.myapplication.view.MainActivity
import com.example.myapplication.view.MealAdapter
import com.example.myapplication.view.OnButtonClick
import com.example.myapplication.view.favActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainDish : AppCompatActivity(), OnButtonClick {
    lateinit var categoryView: TextView
    lateinit var homebtn:Button
    lateinit var favbtn: Button
    lateinit var accountbtn:Button
    lateinit var mealsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_dish)
        favbtn= findViewById(R.id.favbtn)
        categoryView= findViewById(R.id.CategoryView)
        val incomingIntent: Intent = intent // get intent
        categoryView.text= incomingIntent.getStringExtra("categoryName")
        val categoryString:String= categoryView.text.toString()
        mealsRecyclerView = findViewById(R.id.mealsRecyclerView)
        val onButtonClick = this

        lifecycleScope.launch(Dispatchers.IO) {
            val resultmeals = RetroBuilder.service.getMealsByCategory(categoryString)
            withContext(Dispatchers.Main) {
               // textView.text = "Recived ${resultCategories.body()?.categoriesArray?.size}"
                Log.i("meals", "on response: ${resultmeals.body()}")
                val adapter = resultmeals.body()?.mealsArrayList?.let { MealAdapter(it,this@MainDish, onButtonClick) }
                mealsRecyclerView.layoutManager= LinearLayoutManager(this@MainDish, LinearLayoutManager.VERTICAL, false)
               mealsRecyclerView.adapter =adapter

            }
        }


        homebtn= findViewById(R.id.btnhome)
        homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })
        favbtn=findViewById(R.id.favbtn)
        favbtn.setOnClickListener({
            val outIntent = Intent (this , favActivity::class.java)
            startActivity(outIntent)
        })

        accountbtn=findViewById(R.id.accountbtn)
        accountbtn.setOnClickListener({
            val outIntent = Intent (this , AccountActivity::class.java)
            startActivity(outIntent)
        })
    }

    override fun favbtnclick(meal: Meals) {
            lifecycleScope.launch {
                var result:Long
                withContext(Dispatchers.IO) {
                    result= MealsDatabase.getinstanceDatabase(this@MainDish).getmealsDao().insert(meal)
            }
                if (result>0){
                    Toast.makeText(this@MainDish,"Meal Added to Favourites", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@MainDish,"Meal Not Added to Favourites", Toast.LENGTH_SHORT).show()
                }
            }
    }



    override fun deletebtnclick(meal: Meals) {
        lifecycleScope.launch {
            val result: Int
            withContext(Dispatchers.IO) {
                 result = MealsDatabase.getinstanceDatabase(this@MainDish).getmealsDao().delete(meal)
            }
            if (result > 0) {
                Snackbar.make(mealsRecyclerView, "Meal Removed", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(mealsRecyclerView, "Meal Not Removed", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
