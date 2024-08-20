package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.Model.netwrok.RetroBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class suggestMealFragment : Fragment() {
    // TODO: Rename and change types of parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

lateinit var btnexit:Button
lateinit var RandomMealName:TextView
lateinit var RandomMealCategory:TextView
lateinit var RandomMealArea:TextView
lateinit var RandomMealImg: ImageView
lateinit var imgUrl:String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_suggest_meal, container, false)
        btnexit=view.findViewById(R.id.exitbtn)

        RandomMealImg=view.findViewById(R.id.RandomMealImg)
        RandomMealName=view.findViewById( R.id.RandomMealName)
        RandomMealCategory=view.findViewById(R.id.RandoCategroy)
        RandomMealArea=view.findViewById(R.id.RandomMealArea)

        btnexit.setOnClickListener({
            val manager = parentFragmentManager
            val transaction = manager.beginTransaction()
            transaction.remove(this)
            transaction.commit()        })


        viewLifecycleOwner.lifecycleScope.launch {
            val resultRandomMeal= RetroBuilder.service.GetRandomMeal()
            withContext(Dispatchers.Main){
                RandomMealName.text=resultRandomMeal.body()?.Randomeals?.get(0)?.strMeal
                RandomMealCategory.text=resultRandomMeal.body()?.Randomeals?.get(0)?.strCategory
                RandomMealArea.text=resultRandomMeal.body()?.Randomeals?.get(0)?.strArea
                imgUrl=resultRandomMeal.body()?.Randomeals?.get(0)?.strMealThumb.toString()
                Glide.with(this@suggestMealFragment)
                    .load(imgUrl)
                    .centerCrop()
                    .into(RandomMealImg)
            }
            }

        RandomMealName.text



        return view
    }


}