package com.example.myapplication.view

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R

class MealDescriptionActivity : AppCompatActivity()  {
    lateinit var title: TextView
    lateinit var img: ImageView
    lateinit var video: VideoView
    lateinit var videoUrl:String
    lateinit var ingredientbtn:Button
    lateinit var instructionbtn:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_meal_description)


            title=findViewById(R.id.title)
            title.text=intent.getStringExtra("MealName")
            img=findViewById(R.id.imageViewDetails)
            val incomingIntent = intent
            val imageUrl = incomingIntent.getStringExtra("MealImg")
            val mealName:String = incomingIntent.getStringExtra("MealName").toString()
            Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(img)

        ingredientbtn = findViewById(R.id.ingredientsbtn)
       ingredientbtn.setOnClickListener({
            val dynamicFragment = IngredientsFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
           val bundle = Bundle()
           bundle.putString("mealName", mealName)
           dynamicFragment.arguments = bundle
            transaction.replace(R.id.ingredientsFragment, dynamicFragment)
            transaction.commit()

        })
        instructionbtn = findViewById(R.id.instrctbtn)
        instructionbtn.setOnClickListener({
            val dynamicFragment = InstructionsFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            val bundle = Bundle()
            bundle.putString("mealName", mealName)
            dynamicFragment.arguments = bundle
            transaction.replace(R.id.ingredientsFragment, dynamicFragment)
            transaction.commit()
        })


    }


}