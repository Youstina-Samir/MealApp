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
import com.example.myapplication.Model.ConvertMealsToFav
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.FavoriteMeal
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.convertFavmealToMeals
import com.example.myapplication.R
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.view.adapters.MealAdapter
import com.example.myapplication.view.signIn.AccountActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealsByCategory : AppCompatActivity(), OnButtonClick {
    lateinit var categoryView: TextView
    lateinit var homebtn:Button
    lateinit var favbtn: Button
    lateinit var accountbtn:Button
    lateinit var adapter: MealAdapter
    lateinit var viewModel: FilterViewModel
    lateinit var mealsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_dish)
        setUpViewModel()
        favbtn= findViewById(R.id.favbtn)
        categoryView= findViewById(R.id.CategoryView)
        val incomingIntent: Intent = intent // get intent
        categoryView.text= incomingIntent.getStringExtra("categoryName")
        val categoryString:String= categoryView.text.toString()

        val onButtonClick = this

        viewModel.getMealsByCategory(categoryString)
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@MealsByCategory,value, Toast.LENGTH_SHORT).show()
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


        mealsRecyclerView = findViewById(R.id.mealsRecyclerView)
         adapter = MealAdapter(ArrayList(), this, onButtonClick)
        mealsRecyclerView.layoutManager= LinearLayoutManager(this@MealsByCategory, LinearLayoutManager.VERTICAL, false)
        mealsRecyclerView.adapter =adapter




        homebtn= findViewById(R.id.btnhome)
        homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })
        val searchbtn=findViewById<Button>(R.id.searchbtn)
        searchbtn.setOnClickListener({
            val outIntent = Intent (this , SearchActivity::class.java)
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

    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this).getmealsDao()
        val factory= FilterFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FilterViewModel::class.java)
    }

    override fun favbtnclick(meal: Meals) {
        viewModel.addMealToFav(meal)
     /*  if(FirebaseAuth.getInstance().currentUser==null){
            Toast.makeText(this,"Please Sign in to add meal to Favourites", Toast.LENGTH_SHORT).show()
        } else{
            lifecycleScope.launch {
               var userID= FirebaseAuth.getInstance().currentUser?.uid
                Log.d("added to fav", "User ID: ${userID}")
              val FavMeal= ConvertMealsToFav(meal,userID!!)
                var result:Long
                withContext(Dispatchers.IO) {

                    result= MealsDatabase.getinstanceDatabase(this@MealsByCategory).getmealsDao().insert(FavMeal)
            }
                if (result>0){
                    Toast.makeText(this@MealsByCategory,"Meal Added to Favourites", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@MealsByCategory,"Meal Not Added to Favourites", Toast.LENGTH_SHORT).show()
                }
            }
        }*/
    }



   override fun deletebtnclick(meal: Meals) {
       viewModel.deleteMealFromFav(meal)
       /*
       if(FirebaseAuth.getInstance().currentUser?.uid==null) {
           Toast.makeText(this, "Please Sign in to remove meal from Favourites", Toast.LENGTH_SHORT)
               .show()
       }
       else{
           lifecycleScope.launch {
               val result: Int
               var userID = FirebaseAuth.getInstance().currentUser?.uid
               val FavMeal = ConvertMealsToFav(meal, userID!!)
               val mealToDelete = MealsDatabase.getinstanceDatabase(this@MealsByCategory).getmealsDao()
                   .getAll().firstOrNull { it.idMeal == FavMeal.idMeal && it.userId == FavMeal.userId }

               Log.d("deleted from fav", "data:${FavMeal.id} ${userID}, ${FavMeal.strMeal}")
               if (mealToDelete != null) {
               withContext(Dispatchers.IO) {
                   result = MealsDatabase.getinstanceDatabase(this@MealsByCategory).getmealsDao()
                       .delete(FavMeal)
               }
               if (result > 0) {
                   Snackbar.make(mealsRecyclerView, "Meal Removed", Snackbar.LENGTH_SHORT).show()
               } else {
                   Snackbar.make(mealsRecyclerView, "Meal Not Removed", Snackbar.LENGTH_SHORT)
                       .show()
               } }else {Toast.makeText(this@MealsByCategory,"meal not found in favourites", Toast.LENGTH_SHORT).show()}
           }
       }*/
    }


}
