package com.example.myapplication.controller

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.VideoView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.controller.ui.main.SectionsPagerAdapter
import com.example.myapplication.databinding.ActivityMealDetailsBinding
import com.example.myapplication.netwrok.RetroBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealDetails : AppCompatActivity() {

    private lateinit var binding: ActivityMealDetailsBinding
    lateinit var title: TextView
    lateinit var img: ImageView
    lateinit var video: VideoView
    lateinit var videoUrl:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)
        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }


        title=findViewById(R.id.title)
        img=findViewById(R.id.imageViewDetails)
      //  video=findViewById(R.id.videoView)
        val incomingIntent = intent
        val imageUrl = incomingIntent.getStringExtra("MealImg")
        val mealName:String = incomingIntent.getStringExtra("MealName").toString()
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(img)


         lifecycleScope.launch(Dispatchers.IO) {
              val resultmeals = RetroBuilder.service.getMealByName(mealName)
              withContext(Dispatchers.Main) {
                  Log.i("mealsDescrption", "on response: ${resultmeals.body()}")
                  /*val adapter = resultmeals.body()?.mealsArrayList?.let { MealAdapter(it,this@MainDish, onButtonClick) }
                  mealsRecyclerView.layoutManager= LinearLayoutManager(this@MainDish, LinearLayoutManager.VERTICAL, false)
                  mealsRecyclerView.adapter =adapter*/
                  title.text=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strMeal.toString()
                  videoUrl=resultmeals.body()?.mealsDescriptionArray?.get(0)?.strYoutube.toString()
              }

          }
       // video.setVideoPath(videoUrl)
     //   video.start()

    }
}