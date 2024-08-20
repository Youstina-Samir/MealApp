package com.example.myapplication

import androidx.annotation.NonNull
import com.google.gson.annotations.SerializedName
import androidx.room.Entity
import androidx.room.PrimaryKey



data class MealList (

    @SerializedName("meals" ) var mealsArrayList : ArrayList<Meals> = arrayListOf()

)
@Entity(tableName = "Meals_table")
data class Meals(
    @PrimaryKey
    @NonNull
    @SerializedName("strMeal") var strMeal: String = "", // Non-nullable with default value
    @SerializedName("strMealThumb") var strMealThumb: String? = null, // Nullable for Retrofit
    @SerializedName("idMeal") var idMeal: String? = null // Nullable for Retrofit
)

data class MealDescriptionArray(
    @SerializedName("meals" ) var mealsDescriptionArray : ArrayList<MealDescription> = arrayListOf()
)
data class MealDescription(
    @SerializedName("idMeal"                      ) var idMeal                      : String? = null,
    @SerializedName("strMeal"                     ) var strMeal                     : String? = null,
    @SerializedName("strDrinkAlternate"           ) var strDrinkAlternate           : String? = null,
    @SerializedName("strCategory"                 ) var strCategory                 : String? = null,
    @SerializedName("strArea"                     ) var strArea                     : String? = null,
    @SerializedName("strInstructions"             ) var strInstructions             : String? = null,
    @SerializedName("strMealThumb"                ) var strMealThumb                : String? = null,
    @SerializedName("strTags"                     ) var strTags                     : String? = null,
    @SerializedName("strYoutube"                  ) var strYoutube                  : String? = null,
    @SerializedName("strIngredient1"              ) var strIngredient1              : String? = null,
    @SerializedName("strIngredient2"              ) var strIngredient2              : String? = null,
    @SerializedName("strIngredient3"              ) var strIngredient3              : String? = null,
    @SerializedName("strIngredient4"              ) var strIngredient4              : String? = null,
    @SerializedName("strIngredient5"              ) var strIngredient5              : String? = null,
    @SerializedName("strIngredient6"              ) var strIngredient6              : String? = null,
    @SerializedName("strIngredient7"              ) var strIngredient7              : String? = null,
    @SerializedName("strIngredient8"              ) var strIngredient8              : String? = null,
    @SerializedName("strIngredient9"              ) var strIngredient9              : String? = null,
    @SerializedName("strIngredient10"             ) var strIngredient10             : String? = null,
    @SerializedName("strIngredient11"             ) var strIngredient11             : String? = null,
    @SerializedName("strIngredient12"             ) var strIngredient12             : String? = null,
    @SerializedName("strIngredient13"             ) var strIngredient13             : String? = null,
    @SerializedName("strIngredient14"             ) var strIngredient14             : String? = null,
    @SerializedName("strIngredient15"             ) var strIngredient15             : String? = null,
    @SerializedName("strIngredient16"             ) var strIngredient16             : String? = null,
    @SerializedName("strIngredient17"             ) var strIngredient17             : String? = null,
    @SerializedName("strIngredient18"             ) var strIngredient18             : String? = null,
    @SerializedName("strIngredient19"             ) var strIngredient19             : String? = null,
    @SerializedName("strIngredient20"             ) var strIngredient20             : String? = null,
    @SerializedName("strMeasure1"                 ) var strMeasure1                 : String? = null,
    @SerializedName("strMeasure2"                 ) var strMeasure2                 : String? = null,
    @SerializedName("strMeasure3"                 ) var strMeasure3                 : String? = null,
    @SerializedName("strMeasure4"                 ) var strMeasure4                 : String? = null,
    @SerializedName("strMeasure5"                 ) var strMeasure5                 : String? = null,
    @SerializedName("strMeasure6"                 ) var strMeasure6                 : String? = null,
    @SerializedName("strMeasure7"                 ) var strMeasure7                 : String? = null,
    @SerializedName("strMeasure8"                 ) var strMeasure8                 : String? = null,
    @SerializedName("strMeasure9"                 ) var strMeasure9                 : String? = null,
    @SerializedName("strMeasure10"                ) var strMeasure10                : String? = null,
    @SerializedName("strMeasure11"                ) var strMeasure11                : String? = null,
    @SerializedName("strMeasure12"                ) var strMeasure12                : String? = null,
    @SerializedName("strMeasure13"                ) var strMeasure13                : String? = null,
    @SerializedName("strMeasure14"                ) var strMeasure14                : String? = null,
    @SerializedName("strMeasure15"                ) var strMeasure15                : String? = null,
    @SerializedName("strMeasure16"                ) var strMeasure16                : String? = null,
    @SerializedName("strMeasure17"                ) var strMeasure17                : String? = null,
    @SerializedName("strMeasure18"                ) var strMeasure18                : String? = null,
    @SerializedName("strMeasure19"                ) var strMeasure19                : String? = null,
    @SerializedName("strMeasure20"                ) var strMeasure20                : String? = null,
    @SerializedName("strSource"                   ) var strSource                   : String? = null,
    @SerializedName("strImageSource"              ) var strImageSource              : String? = null,
    @SerializedName("strCreativeCommonsConfirmed" ) var strCreativeCommonsConfirmed : String? = null,
    @SerializedName("dateModified"                ) var dateModified                : String? = null

)

/*
data class Meals (
    @SerializedName("strMeal"      ) var strMeal      : String? = null,
    @SerializedName("strMealThumb" ) var strMealThumb : String? = null,
    @SerializedName("idMeal"       ) var idMeal       : String? = null

)

@Entity(tableName = "Meals_table")
data class MealsRoom(
    @PrimaryKey
    @NonNull
    @SerializedName("strMeal"      ) var strMeal      : String,
    @SerializedName("strMealThumb" ) var strMealThumb : String,
    @SerializedName("idMeal"       ) var idMeal       : String

)
*/