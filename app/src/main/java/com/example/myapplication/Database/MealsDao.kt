package com.example.myapplication.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.Meals

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(meal: Meals) : Long
    @Delete
    suspend fun delete(meal: Meals) : Int
    @Query("SELECT * FROM Meals_table")
    suspend fun getAll(): List<Meals>

}