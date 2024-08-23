package com.example.myapplication.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.ConvertMealsToFav
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.MealDescription
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.convertMealDescriptionToMeals
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.R
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.view.adapters.MealAdapter
import com.example.myapplication.view.adapters.MealDescriptionAdapter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchActivity : AppCompatActivity(), OnButtonClick {
    lateinit var viewModel: FilterViewModel
    lateinit var RecyclerView: RecyclerView
    lateinit var adapter: MealAdapter
    lateinit var adapter2 : MealDescriptionAdapter
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
        SpinnerChoice=""

        var spinnerOptions = arrayOf("Meal name","Category", "Nationality", "Main ingredients")
        spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerOptions)
        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                SpinnerChoice="Meal name"
                setAdapterBasedOnChoice()

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                SpinnerChoice=spinnerOptions[position]
                setAdapterBasedOnChoice()
            }
        }
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
               if (SpinnerChoice == "Meal name") {
                    viewModel.getMealsByName(newText.toString())
                   adapter2.notifyDataSetChanged()
               }
               else if (SpinnerChoice == "Nationality") {
                   viewModel.getMealsByArea(newText.toString())
                   adapter.notifyDataSetChanged()
               }
                else if (SpinnerChoice == "Category") {
                    viewModel.getMealsByCategory(newText.toString())
                } else if (SpinnerChoice == "Main ingredients") {
                    viewModel.getMealsByIngredient(newText.toString())
                }

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
       val observerMeals2: Observer<ArrayList<MealDescription>> = object : Observer<ArrayList<MealDescription>> {
            override fun onChanged(value: ArrayList<MealDescription>) {
                adapter2.meallist = value
                adapter2.notifyDataSetChanged()
            }
        }
        viewModel.msg.observe(this, observerMsg)
        viewModel.Meals.observe(this, observerMeals)
        viewModel.Meals2.observe(this, observerMeals2)

        RecyclerView= findViewById(R.id.RecyclerViewerSearch)
        RecyclerView.layoutManager= LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        adapter= MealAdapter(arrayListOf(), this ,this)
        adapter2= MealDescriptionAdapter(arrayListOf(), this ,this)




    }
    private fun setAdapterBasedOnChoice() {
        if(SpinnerChoice=="Meal name")
        { RecyclerView.adapter=adapter2}
        else
        {  RecyclerView.adapter=adapter}
    }
    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this).getmealsDao()
        val factory= FilterFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FilterViewModel::class.java)
    }

    override fun favbtnclick(meal: Meals) {
        lifecycleScope.launch {
            var result:Long
            withContext(Dispatchers.IO) {
                var userID= FirebaseAuth.getInstance().currentUser?.uid
                Log.d("added to fav", "User ID: ${userID}")
                val FavMeal= ConvertMealsToFav(meal,userID!!)
                result= MealsDatabase.getinstanceDatabase(this@SearchActivity).getmealsDao().insert(FavMeal)
            }
            if (result>0){
                Toast.makeText(this@SearchActivity,"Meal Added to Favourites", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@SearchActivity,"Meal Not Added to Favourites", Toast.LENGTH_SHORT).show()
            }
        }    }

    override fun favbtnForMealDescritpion(meal: MealDescription) {
        lifecycleScope.launch {
            val mealToSave = convertMealDescriptionToMeals(meal)
            var userID= FirebaseAuth.getInstance().currentUser?.uid
            Log.d("added to fav", "User ID: ${userID}")
            val FavMeal= ConvertMealsToFav(mealToSave,userID!!)
            var result: Long
            withContext(Dispatchers.IO) {
                result = MealsDatabase.getinstanceDatabase(this@SearchActivity).getmealsDao().insert(FavMeal)
            }
            if (result > 0) {
                Toast.makeText(this@SearchActivity, "Meal Added to Favourites", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SearchActivity, "Meal Not Added to Favourites", Toast.LENGTH_SHORT).show()
            }
        }    }
    override fun deletebtnclick(meal: Meals) {
        TODO("Not yet implemented")
    }
}