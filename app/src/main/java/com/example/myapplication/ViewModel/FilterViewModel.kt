package com.example.myapplication.ViewModel
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.ConvertMealsToFav
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.MealDescription
import com.example.myapplication.Model.MealList
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.convertMealDescriptionToMeals
import com.example.myapplication.Model.netwrok.SimpleService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import kotlinx.coroutines.withContext
import java.io.IOException


class FilterViewModel (private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {
    private val _Meals = MutableLiveData<ArrayList<Meals>>()
    val Meals: LiveData<ArrayList<Meals>> = _Meals
    private val _Meals2 = MutableLiveData<ArrayList<MealDescription>>()
    val Meals2: LiveData<ArrayList<MealDescription>> = _Meals2
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun getMealsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val resultMeals = retrofit.getMealsByCategory(category)
                withContext(Dispatchers.Main) {
                    if (resultMeals.body()?.mealsArrayList.isNullOrEmpty()) {
                        _msg.postValue("No products found")
                    } else {
                        _Meals.postValue(resultMeals.body()?.mealsArrayList!!)
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _msg.postValue("Network error. Please check your connection.")
                }
            }
        }
    }
    fun getMealsByName(meal: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
            val resultMeals = retrofit.getMealByName(meal)
            withContext(Dispatchers.Main) {
                if (resultMeals.body()?.mealsDescriptionArray.isNullOrEmpty()) {
                    _msg.postValue("No products found")
                } else {
                    _Meals2.postValue(resultMeals.body()?.mealsDescriptionArray!!)
                }
            }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    _msg.postValue("Network error. Please check your connection.") }
                }
        }
    }
    fun getMealsByArea(area: String) {
        viewModelScope.launch(Dispatchers.IO) {
        try{
            val resultMeals = retrofit.getMealsByArea(area)
            withContext(Dispatchers.Main) {
                if (resultMeals.body()?.mealsArrayList.isNullOrEmpty()) {
                    _msg.postValue("No products found")
                } else {
                    _Meals.postValue(resultMeals.body()?.mealsArrayList!!)
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _msg.postValue("Network error. Please check your connection.")
            }
        }
    }
    }
    fun getMealsByIngredient(ingredient: String) {
        viewModelScope.launch(Dispatchers.IO) { try{
            val resultMeals = retrofit.getMealsByIngredient(ingredient)
            withContext(Dispatchers.Main) {
                if (resultMeals.body()?.mealsArrayList.isNullOrEmpty()) {
                    _msg.postValue("No products found")
                } else {
                    _Meals.postValue(resultMeals.body()?.mealsArrayList!!)
                }
            } } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                _msg.postValue("Network error. Please check your connection.")
            }
        }
    }
    }
    fun addMealToFav(meal: Meals) {
        if(FirebaseAuth.getInstance().currentUser==null){
           _msg.postValue( "Please Sign in to add meal to Favourites")
        } else{
            viewModelScope.launch {
                var userID= FirebaseAuth.getInstance().currentUser?.uid
                Log.d("added to fav", "User ID: ${userID}")
                val FavMeal= ConvertMealsToFav(meal,userID!!)
                var result:Long
                withContext(Dispatchers.IO) {
                    val existingMeal =dao.getAll()
                        .firstOrNull { it.idMeal == FavMeal.idMeal && it.userId == FavMeal.userId }

                    if (existingMeal == null) { // If it doesn't exist, insert it
                        result =dao.insert(FavMeal)
                    } else {
                        result = -1L
                    }
                    //result= dao.insert(FavMeal)
                }
                if (result>0){
                 _msg.postValue  ( "Meal Added to Favourites")
                }
                else if (result==-1L){
                    _msg.postValue  ( "Meal Already Added to Favourites")
                }
                else{
                    _msg.postValue  ( "Meal Not Added to Favourites")
                }
            }
        }
    }
    fun deleteMealFromFav(meal: Meals) {
        if(FirebaseAuth.getInstance().currentUser?.uid==null) {
            _msg.postValue( "Please Sign in to remove meal from Favourites",)
        }
        else{
            viewModelScope.launch {
                val result: Int
                var userID = FirebaseAuth.getInstance().currentUser?.uid
                val FavMeal = ConvertMealsToFav(meal, userID!!)
                val mealToDelete =dao.getAll().firstOrNull { it.idMeal == FavMeal.idMeal && it.userId == FavMeal.userId }

                Log.d("deleted from fav", "data:${FavMeal.id} ${userID}, ${FavMeal.strMeal}")
                if (mealToDelete != null) {
                    withContext(Dispatchers.IO) {
                        result = dao.delete(mealToDelete)
                    }
                    if (result > 0) {
                        _msg.postValue("Meal Removed")
                    } else {
                        _msg.postValue("Meal Not Removed")
                    }
                }else {_msg.postValue("meal not found in favourites")}
            }
        }
    }

}
class FilterFactory   (private val dao: MealsDao, private val retrofit: SimpleService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FilterViewModel::class.java)) {
            return FilterViewModel(dao, retrofit) as T}
        else
            throw IllegalArgumentException("Unknown ViewModel")
    }

}