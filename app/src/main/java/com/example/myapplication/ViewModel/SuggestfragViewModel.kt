package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.RandomMeal
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.Model.netwrok.SimpleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class SuggestfragViewModel (private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    private val _RandomMeal = MutableLiveData<Response<RandomMeal>>()
    val RandomMeal: LiveData<Response<RandomMeal>> = _RandomMeal

    fun GetRandomMeal(){
    viewModelScope.launch(Dispatchers.IO){ try{
        val resultRandomMeal= RetroBuilder.service.GetRandomMeal()
        withContext(Dispatchers.Main){
            _RandomMeal.postValue(resultRandomMeal)
        } } catch (e: IOException) {
        withContext(Dispatchers.Main) {
            _msg.postValue("Network error. Please check your connection.")
            }
        }
      }
    }
}
class SuggestFregFactory   (private val dao: MealsDao, private val retrofit: SimpleService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuggestfragViewModel::class.java)) {
            return SuggestfragViewModel(dao, retrofit) as T}
        else
            throw IllegalArgumentException("Unknown ViewModel")
    }

}