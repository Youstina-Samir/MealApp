package com.example.myapplication.ViewModel

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
import com.example.myapplication.Model.FavoriteMeal
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.favoriteMealToMeal
import com.example.myapplication.Model.netwrok.SimpleService
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavViewModel(private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {

    private val _Meals = MutableLiveData<List<Meals>>()
    val Meals: LiveData<List<Meals>> = _Meals
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun getFavMeals() {
        viewModelScope.launch {
            val userId = FirebaseAuth.getInstance().currentUser?.uid
            var storedMeals: List<FavoriteMeal>
            withContext(Dispatchers.IO) {
                storedMeals = dao.getAll()
            }
            val filteredMeals = if (userId != null) {
                storedMeals.filter { it.userId == userId }
            } else {
                emptyList()
            }
            var mealsList = filteredMeals.map { favoriteMealToMeal(it) }
            _Meals.postValue(mealsList )

        }
    }
    fun addMealToFav(meal: Meals) {
        _msg.postValue("Meal already Added")

    }
    fun deleteMealFromFav(meal: Meals) {
        if(FirebaseAuth.getInstance().currentUser==null) {
         _msg.postValue  ("Please Sign in to remove meal from Favourites")}
        else{
                viewModelScope.launch(Dispatchers.IO) {
                var userID= FirebaseAuth.getInstance().currentUser?.uid
                val FavMeal= ConvertMealsToFav(meal,userID!!)
                val mealToDelete = dao.getAll().firstOrNull { it.idMeal == FavMeal.idMeal && it.userId == FavMeal.userId }

                Log.d("deleted from fav", "data:${FavMeal.id} ${userID}, ${FavMeal.strMeal}")
                if (mealToDelete != null) {
                    val   result = dao.delete(mealToDelete!!);
                    withContext(Dispatchers.Main) {
                        if (result > 0) {
                            _msg.postValue("Meal Removed")
                        } else {
                            _msg.postValue("Meal Not Removed")
                        }
                    }
                }else {
                    _msg.postValue("Meal Not found")}

                val   newlist = dao.getAll()
                withContext(Dispatchers.Main) {
                    var mealsList = newlist.map { favoriteMealToMeal(it) }
                  _Meals.postValue(mealsList)
                    getFavMeals()
                }
            }}

    }
}
class FavFactory   (private val dao: MealsDao, private val retrofit: SimpleService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavViewModel::class.java)) {
            return FavViewModel(dao, retrofit) as T}
        else
            throw IllegalArgumentException("Unknown ViewModel")
    }

}