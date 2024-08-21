package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.MealList
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.netwrok.SimpleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FilterViewModel (private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {
    private val _Meals = MutableLiveData<ArrayList<Meals>>()
    val Meals: LiveData<ArrayList<Meals>> = _Meals
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg

    fun getMealsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultMeals = retrofit.getMealsByCategory(category)
            withContext(Dispatchers.Main) {
                if (resultMeals.body()?.mealsArrayList.isNullOrEmpty()) {
                    _msg.postValue("No products found")
                } else {
                    _Meals.postValue(resultMeals.body()?.mealsArrayList!!)
                }
            }
        }
    }
    fun getMealsByArea(area: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultMeals = retrofit.getMealsByArea(area)
            withContext(Dispatchers.Main) {
                if (resultMeals.body()?.mealsArrayList.isNullOrEmpty()) {
                    _msg.postValue("No products found")
                } else {
                    _Meals.postValue(resultMeals.body()?.mealsArrayList!!)
                }
            }
        }
    }
    fun getMealsByIngredient(ingredient: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultMeals = retrofit.getMealsByIngredient(ingredient)
            withContext(Dispatchers.Main) {
                if (resultMeals.body()?.mealsArrayList.isNullOrEmpty()) {
                    _msg.postValue("No products found")
                } else {
                    _Meals.postValue(resultMeals.body()?.mealsArrayList!!)
                }
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