package com.example.myapplication.view

import com.example.myapplication.Model.Meals

interface OnButtonClick {
    fun favbtnclick(meal: Meals)
    fun deletebtnclick(meal: Meals)
}