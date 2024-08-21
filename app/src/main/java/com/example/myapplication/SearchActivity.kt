package com.example.myapplication

import android.database.MatrixCursor
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Areas
import com.example.myapplication.Model.Category
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.ViewModel.MainActivityViewModel
import com.example.myapplication.ViewModel.MainFactory
import com.example.myapplication.view.MealAdapter
import com.example.myapplication.view.OnButtonClick

class SearchActivity : AppCompatActivity(), OnButtonClick {
    lateinit var viewModel: FilterViewModel
    lateinit var RecyclerView: RecyclerView
    lateinit var adapter: MealAdapter
    lateinit var searchView: SearchView
    lateinit var spinner: Spinner
    lateinit var SpinnerChoice:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search)
        setUpViewModel()

        val onButtonClick = this

        searchView =findViewById<SearchView>(R.id.searchView)
         spinner= findViewById<Spinner>(R.id.spinner)

        var spinnerOptions = arrayOf("Category", "Nationality", "Main ingredients")
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions)
        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                SpinnerChoice="Category"
            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                SpinnerChoice=spinnerOptions[position]

            }
        }
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (SpinnerChoice == "Category") {
                    viewModel.getMealsByCategory(query.toString())
                } else if (SpinnerChoice == "Nationality") {
                    viewModel.getMealsByArea(query.toString())
                } else if (SpinnerChoice == "Main ingredients") {
                    viewModel.getMealsByCategory(query.toString())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return true
            }

        })




        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@SearchActivity,value, Toast.LENGTH_SHORT).show()
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

        RecyclerView= findViewById(R.id.RecyclerViewerSearch)
        RecyclerView.layoutManager= LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        adapter= MealAdapter(arrayListOf(), this ,this)
        RecyclerView.adapter=adapter


    }
    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this).getmealsDao()
        val factory= FilterFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FilterViewModel::class.java)
    }

    override fun favbtnclick(meal: Meals) {
        TODO("Not yet implemented")
    }

    override fun deletebtnclick(meal: Meals) {
        TODO("Not yet implemented")
    }
}