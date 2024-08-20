package com.example.myapplication.Model.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.Model.Meals

@Database (entities = [Meals::class], version = 1)

abstract class MealsDatabase : RoomDatabase() {
    abstract fun getmealsDao(): MealsDao
    companion object{
        @Volatile
        private var instanceDatabase: MealsDatabase? = null
        fun getinstanceDatabase(context: Context): MealsDatabase {
            return instanceDatabase ?: synchronized(this){
                val tempinstance = Room.databaseBuilder(context, MealsDatabase::class.java, "Meals_database").build()
                instanceDatabase =tempinstance
                tempinstance
            }
        }


    }

}