package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.view.signIn.AccountActivity

class MealDescriptionActivity : AppCompatActivity() {
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

        val  homebtn= findViewById<Button>(R.id.btnhome)
        homebtn.setOnClickListener({
            val outIntent = Intent (this , MainActivity::class.java)
            startActivity(outIntent)
        })
        val favbtn = findViewById<Button>(R.id.favbtn)
        favbtn.setOnClickListener({
            val outIntent = Intent(this, favActivity::class.java)
            startActivity(outIntent)
        })
        val accountbtn =findViewById<Button>(R.id.accountbtn)
        accountbtn.setOnClickListener({
            val outIntent = Intent(this, AccountActivity::class.java)
            startActivity(outIntent)
        })
        val searchbtn=findViewById<Button>(R.id.searchbtn)
        searchbtn.setOnClickListener({
            val outIntent = Intent(this, SearchActivity::class.java)
            startActivity(outIntent)
        })
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


         /*   lifecycleScope.launch(Dispatchers.IO) {
                val resultmeals = RetroBuilder.service.getMealByName(mealName!!)
                withContext(Dispatchers.Main) {
                    Log.i("mealsDescrption", "on response: ${resultmeals.body()}")
                    /*val adapter = resultmeals.body()?.mealsArrayList?.let { MealAdapter(it,this@MainDish, onButtonClick) }
                    mealsRecyclerView.layoutManager= LinearLayoutManager(this@MainDish, LinearLayoutManager.VERTICAL, false)
                    mealsRecyclerView.adapter =adapter*/
                    title.text=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strMeal.toString()
                    videoUrl=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strYoutube.toString()
                    ingredients[0]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient1.toString()
                    ingredients[1]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient2.toString()
                    ingredients[2]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient3.toString()
                    ingredients[3]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient4.toString()
                    ingredients[4]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient5.toString()
                    ingredients[5]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient6.toString()
                    ingredients[6]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient7.toString()
                    ingredients[7]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient8.toString()
                    ingredients[8]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient9.toString()
                    ingredients[9]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient10.toString()
                    ingredients[10]=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strIngredient11.toString()
                }
            }*/
    }
}