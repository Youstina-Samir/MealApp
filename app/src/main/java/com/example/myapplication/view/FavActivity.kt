package com.example.myapplication.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.ConvertMealsToFav
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.FavoriteMeal
import com.example.myapplication.Model.MealDescription
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.favoriteMealToMeal
import com.example.myapplication.R
import com.example.myapplication.view.adapters.MealAdapter
import com.example.myapplication.view.signIn.AccountActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav)
        recycler=findViewById(R.id.favRecycler)
        adapter= MealAdapter(ArrayList<Meals>(),this,this)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter

        /*lifecycleScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            var storedMeals: List<FavoriteMeal>
            withContext(Dispatchers.IO) {
                storedMeals = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().getAll()
            }
            val filteredMeals = if (userId != null) {
            storedMeals.filter { it.userId == userId }.also {
                Log.d("FavActivity", "Filtered meals for userId $userId: $it") // Log filtered meals
            }
        } else {
            emptyList()
        }
            adapter.meallist=storedMeals
            adapter.notifyDataSetChanged()
        }

         */
        lifecycleScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            var storedMeals: List<FavoriteMeal>
            withContext(Dispatchers.IO) {
                storedMeals = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().getAll()
            }
            val filteredMeals = if (userId != null) {
                storedMeals.filter { it.userId == userId }
            } else {
                emptyList()
            }
            // Convert FavoriteMeal to Mealsval
           var mealsList = filteredMeals.map { favoriteMealToMeal(it) }
            adapter.meallist = mealsList // Update adapter with Meals list
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

override fun favbtnForMealDescritpion(meal: MealDescription) {

}


    override fun deletebtnclick(meal: Meals) {
        if(FirebaseAuth.getInstance().currentUser==null) {
            Toast.makeText(this, "Please Sign in to remove meal from Favourites", Toast.LENGTH_SHORT)
                .show()
        }
        else{
        lifecycleScope.launch(Dispatchers.IO) {
            var userID= FirebaseAuth.getInstance().currentUser?.uid
            val FavMeal= ConvertMealsToFav(meal,userID!!)
            val mealToDelete = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao()
                .getAll().firstOrNull { it.idMeal == FavMeal.idMeal && it.userId == FavMeal.userId }

            Log.d("deleted from fav", "data:${FavMeal.id} ${userID}, ${FavMeal.strMeal}")
            if (mealToDelete != null) {
         val   result = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().delete(mealToDelete!!);
            withContext(Dispatchers.Main) {
                if (result > 0) {
                    Snackbar.make(recycler, "Meal Removed", Snackbar.LENGTH_SHORT).show()
                } else {
                    Snackbar.make(recycler, "Meal Not Removed", Snackbar.LENGTH_SHORT).show()
                }
            }
            }else {Toast.makeText(this@favActivity,"meal not found", Toast.LENGTH_SHORT).show()}

             val   newlist = MealsDatabase.getinstanceDatabase(this@favActivity).getmealsDao().getAll()
            withContext(Dispatchers.Main) {
                var mealsList = newlist.map { favoriteMealToMeal(it) }
                adapter.meallist = mealsList
                adapter.notifyDataSetChanged()
            }
        }}


    }
}
