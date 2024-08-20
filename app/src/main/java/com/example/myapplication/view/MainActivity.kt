package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Model.Category
import com.example.myapplication.Model.Database.MealsDatabase

//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import com.example.myapplication.view.CategoriesAdapter
import com.example.myapplication.R
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.ViewModel.MainActivityViewModel
import com.example.myapplication.ViewModel.MainFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var favbtn: Button
    lateinit var btnsuggest: Button
    lateinit var accountbtn: Button
    lateinit var adapter: CategoriesAdapter
    lateinit var viewModel: MainActivityViewModel
    lateinit var CatRecyclerView: RecyclerView
    lateinit var textView: TextView
    lateinit var swip : SwipeRefreshLayout
    //lateinit var category: String


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setUpViewModel()

        viewModel.getCategories()
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@MainActivity,value, Toast.LENGTH_SHORT).show()
            }
        }
        val observerProducts: Observer<ArrayList<Category>> = object : Observer<ArrayList<Category>> {
            override fun onChanged(value: ArrayList<Category>) {
                adapter.categorylist = value
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.msg.observe(this, observerMsg)
        viewModel.Category.observe(this, observerProducts)



        CatRecyclerView = findViewById<RecyclerView>(R.id.recyclerViewTxt)
        adapter = CategoriesAdapter(ArrayList(), this)
        CatRecyclerView.layoutManager= LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        CatRecyclerView.adapter =adapter



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

        }

    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this).getmealsDao()
        val factory=MainFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(MainActivityViewModel::class.java)
    }
    }


