package com.example.myapplication.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Model.Areas
import com.example.myapplication.Model.Category
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.Model.netwrok.SimpleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class MainActivityViewModel (private val dao: MealsDao, private val retrofit: SimpleService) : ViewModel() {
    private val _Category = MutableLiveData<ArrayList<Category>>()
    val Category: LiveData<ArrayList<Category>> = _Category

    private val _Country = MutableLiveData<ArrayList<Areas>>()
    val Country: LiveData<ArrayList<Areas>> = _Country

    private val _msg = MutableLiveData<String>()
    val msg: LiveData<String> = _msg
    init {
         getCategories()
    }

    fun getCategories() {
        viewModelScope.launch (Dispatchers.IO){
            val resultCategories = try { RetroBuilder.service.GetListCategory()?.body()?.categoriesArray
            } catch (e: IOException) {
                // Handle network exceptions here
                _msg.postValue("No internet connection available")
                null
            }
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
    fun getCountries() {
        viewModelScope.launch (Dispatchers.IO){
            val resultCountries =try { RetroBuilder.service.getAreaList()?.body()?.CountiresArrayList
            } catch (e:IOException) {
                // Handle network exceptions here
                _msg.postValue("No internet connection available")
                null
            }
            withContext(Dispatchers.Main) {
                if (resultCountries.isNullOrEmpty()){
                    _msg.postValue("No products found")
                }
                else{
                    _Country.postValue(resultCountries!!)
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