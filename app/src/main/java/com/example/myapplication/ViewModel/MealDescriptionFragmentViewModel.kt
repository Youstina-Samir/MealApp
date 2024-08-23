package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.MealDescriptionArray
import com.example.myapplication.Model.RandomMeal
import com.example.myapplication.Model.netwrok.SimpleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MealDescriptionFragmentViewModel( private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    private val _mealDetail = MutableLiveData<Response<MealDescriptionArray>>()
    val mealDetail: LiveData<Response<MealDescriptionArray>> = _mealDetail

    fun getMealDetail(mealName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val resultMealDetail = retrofit.getMealByName(mealName)
            withContext(Dispatchers.Main) {
                _mealDetail.postValue(resultMealDetail)
            }
        }
    }
}