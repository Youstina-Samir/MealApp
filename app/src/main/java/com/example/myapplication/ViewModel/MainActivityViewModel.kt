package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.Category
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.Model.netwrok.SimpleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivityViewModel (private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {
    private val _Category = MutableLiveData<ArrayList<Category>>()
    val Category: LiveData<ArrayList<Category>> = _Category
    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    init {
         getCategories()
    }

    fun getCategories() {
        viewModelScope.launch (Dispatchers.IO){
            val resultCategories = RetroBuilder.service.GetListCategory()?.body()?.categoriesArray
            withContext(Dispatchers.Main) {
                if (resultCategories.isNullOrEmpty()){
                    _msg.postValue("No products found")
                }
            else{
            _Category.postValue(resultCategories!!)
            }
            }
        }
    }
}
class MainFactory   (private val dao: MealsDao, private val retrofit: SimpleService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
        return MainActivityViewModel(dao, retrofit) as T}
        else
            throw IllegalArgumentException("Unknown ViewModel")
    }

}