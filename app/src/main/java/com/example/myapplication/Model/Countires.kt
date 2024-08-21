package com.example.myapplication.Model

import com.google.gson.annotations.SerializedName

data class Countires (
    @SerializedName("meals" ) var CountiresArrayList : ArrayList<Areas> = arrayListOf()
)


data class Areas (

    @SerializedName("strArea" ) var strArea : String? = null

)