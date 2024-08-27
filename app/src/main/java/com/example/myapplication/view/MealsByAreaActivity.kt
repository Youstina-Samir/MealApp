package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.R
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.view.adapters.MealAdapter
import com.example.myapplication.view.signIn.AccountActivity

class MealsByAreaActivity : AppCompatActivity(), OnButtonClick {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MealAdapter
    lateinit var viewModel: FilterViewModel
    lateinit var areaTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meals_by_area)
        val incomingIntent: Intent = intent // get intent
        val areaName = incomingIntent.getStringExtra("AreaName")
        val areaString=areaName.toString()
        areaTextView=findViewById(R.id.areaTextView)
        areaTextView.text=areaName

        setUpViewModel()

        viewModel.getMealsByArea(areaString)
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@MealsByAreaActivity,value, Toast.LENGTH_SHORT).show()
            }
        }
        val observerMeals: Observer<ArrayList<Meals>> = object : Observer<ArrayList<Meals>> {
            override fun onChanged(value: ArrayList<Meals>) {
                adapter.meallist = value
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.msg.observe(this, observerMsg)
        viewModel.Meals.observe(this, observerMeals)


        recyclerView = findViewById(R.id.mealRecycler)
        adapter = MealAdapter(ArrayList(), this, this)
        recyclerView.layoutManager= LinearLayoutManager(this@MealsByAreaActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

      val  homebtn= findViewById<Button>(R.id.btnhome);
        homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })
        val favbtn = findViewById<Button>(R.id.favbtn)
        favbtn.setOnClickListener({
            val outIntent = Intent(this, favActivity::class.java)
            startActivity(outIntent)
        })
        val accountbtn =findViewById<Button>(R.id.accountbtn)
        accountbtn.setOnClickListener({
            val outIntent = Intent(this, AccountActivity::class.java)
            startActivity(outIntent)
        })
        val searchbtn=findViewById<Button>(R.id.searchbtn)
        searchbtn.setOnClickListener({
            val outIntent = Intent(this, SearchActivity::class.java)
            startActivity(outIntent)
        })

    }

    private fun setUpViewModel(){
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this).getmealsDao()
        val factory= FilterFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FilterViewModel::class.java)

    }

    override fun favbtnclick(meal: Meals) {
        viewModel.addMealToFav(meal)
    }

    override fun deletebtnclick(meal: Meals) {
        viewModel.deleteMealFromFav(meal)
    }


}