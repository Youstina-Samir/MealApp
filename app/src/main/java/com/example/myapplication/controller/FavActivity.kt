package com.example.myapplication.controller

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Database.MealsDatabase
import com.example.myapplication.Meals
import com.example.myapplication.R
import com.example.myapplication.view.MealAdapter
import com.example.myapplication.view.OnButtonClick
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class favActivity : AppCompatActivity() , OnButtonClick {
    lateinit var homebtn:Button
    lateinit var accountbtn:Button
    lateinit var favbtn: Button
    lateinit var deletebtn: Button
    lateinit var recycler: RecyclerView
    lateinit var adapter: MealAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)
        recycler=findViewById(R.id.favRecycler)
        adapter= MealAdapter(ArrayList<Meals>(),this,this)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter

        lifecycleScope.launch {
            var storedMeals: List<Meals>
            withContext(Dispatchers.IO) {
                storedMeals = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().getAll()
            }
            adapter.meallist=storedMeals
            adapter.notifyDataSetChanged()
        }

        homebtn= findViewById(R.id.btnhome)
       homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })

        accountbtn=findViewById(R.id.accountbtn)
        accountbtn.setOnClickListener({
            val outIntent = Intent (this , AccountActivity::class.java)
            startActivity(outIntent)
        })

    }

    override fun favbtnclick(meal: Meals) {
       Toast.makeText(this,"Meal  alreayd Added to Favourites", Toast.LENGTH_SHORT).show()
    }




    override fun deletebtnclick(meal: Meals) {
        lifecycleScope.launch(Dispatchers.IO) {
         val   result = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().delete(meal);
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    Snackbar.make(recycler, "Meal Removed", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(recycler, "Meal Not Removed", Snackbar.LENGTH_SHORT).show()
                }
            }
             val   newlist = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().getAll()
            withContext(Dispatchers.Main) {
                adapter.meallist = newlist
                adapter.notifyDataSetChanged()
            }
        }


    }
}
