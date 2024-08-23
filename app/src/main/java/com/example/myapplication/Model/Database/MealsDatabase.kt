package com.example.myapplication.Model.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.Model.FavoriteMeal
import com.example.myapplication.Model.Meals

@Database (entities = [Meals::class , FavoriteMeal::class], version = 3)

abstract class MealsDatabase : RoomDatabase() {
    abstract fun getmealsDao(): MealsDao
    companion object{
        @Volatile
        private var instanceDatabase: MealsDatabase? = null
        fun getinstanceDatabase(context: Context): MealsDatabase {
            return instanceDatabase ?: synchronized(this){
                val tempinstance = Room.databaseBuilder(context, MealsDatabase::class.java, "fav_table")
                   .fallbackToDestructiveMigration()
                 .build()
                instanceDatabase =tempinstance
                tempinstance
            }
        }


    }


}