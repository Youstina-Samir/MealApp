package com.example.myapplication.view

import com.example.myapplication.Model.FavoriteMeal
import com.example.myapplication.Model.MealDescription
import com.example.myapplication.Model.Meals

interface OnButtonClick {
    fun favbtnclick(meal: Meals)
    fun deletebtnclick(meal: Meals)
    //fun favbtnForMealDescritpion(meal: MealDescription)
    //fun deletbtnForMealDescritpion(meal: MealDescription)

}