package com.example.myapplication.Model.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.Model.FavoriteMeal
import com.example.myapplication.Model.Meals

@Dao
interface MealsDao {
   @Insert(onConflict = OnConflictStrategy.IGNORE)
//     suspend fun insert(meal: Meals) : Long
   suspend fun insert(meal: FavoriteMeal) : Long
 @Delete
   // suspend fun delete(meal: Meals) : Int
   suspend fun delete(meal: FavoriteMeal) : Int

   @Query("SELECT * FROM fav_table")
    suspend fun getAll(): List<FavoriteMeal>



}