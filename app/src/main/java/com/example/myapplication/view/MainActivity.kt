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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Model.Areas
import com.example.myapplication.Model.Category
import com.example.myapplication.Model.Database.MealsDatabase

//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.myapplication.view.CategoriesAdapter
import com.example.myapplication.R
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.SearchActivity
import com.example.myapplication.ViewModel.MainActivityViewModel
import com.example.myapplication.ViewModel.MainFactory

class MainActivity : AppCompatActivity() {
    lateinit var favbtn: Button
    lateinit var btnsuggest: Button
    lateinit var accountbtn: Button
    lateinit var searchbtn: Button
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var countriesAdapter: CountiresAdapter
    lateinit var viewModel: MainActivityViewModel
    lateinit var recyclerViewCategrories: RecyclerView
    lateinit var recyclerViewCountries: RecyclerView
    lateinit var textView: TextView
    lateinit var swip : SwipeRefreshLayout
    //lateinit var category: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setUpViewModel()

        viewModel.getCategories()
        viewModel.getCountries()
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@MainActivity,value, Toast.LENGTH_SHORT).show()
            }
        }
        val observerProducts: Observer<ArrayList<Category>> = object : Observer<ArrayList<Category>> {
            override fun onChanged(value: ArrayList<Category>) {
                categoriesAdapter.categorylist = value
                categoriesAdapter.notifyDataSetChanged()
            }
        }
        val observerCountries: Observer<ArrayList<Areas>> = object : Observer<ArrayList<Areas>> {
            override fun onChanged(value: ArrayList<Areas>) {
                countriesAdapter.Countireslist = value
                countriesAdapter.notifyDataSetChanged()
            }}
        viewModel.Country.observe(this, observerCountries)
        viewModel.msg.observe(this, observerMsg)
        viewModel.Category.observe(this, observerProducts)



        recyclerViewCategrories = findViewById<RecyclerView>(R.id.recyclerViewTxt)
        categoriesAdapter = CategoriesAdapter(ArrayList(), this)
        recyclerViewCategrories.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCategrories.adapter =categoriesAdapter

        recyclerViewCountries = findViewById<RecyclerView>(R.id.recyclerViewCountries)
        countriesAdapter = CountiresAdapter(ArrayList(), this)
        recyclerViewCountries.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewCountries.adapter =countriesAdapter



            btnsuggest = findViewById(R.id.suggestbtn)
            btnsuggest.setOnClickListener({
                val dynamicFragment = suggestMealFragment()
                val manager = supportFragmentManager
                val transaction = manager.beginTransaction()
                transaction.replace(R.id.suggestFragment, dynamicFragment)
                transaction.commit()

            })
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
            searchbtn=findViewById(R.id.searchbtn)
            searchbtn.setOnClickListener({
                val outIntent = Intent(this, SearchActivity::class.java)
                startActivity(outIntent)
            })

        }

    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this).getmealsDao()
        val factory=MainFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(MainActivityViewModel::class.java)
    }
    }


