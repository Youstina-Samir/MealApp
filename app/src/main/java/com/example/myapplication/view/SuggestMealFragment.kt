package com.example.myapplication.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.RandomMeal
import com.example.myapplication.R
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.ViewModel.SuggestFregFactory
import com.example.myapplication.ViewModel.SuggestfragViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response


class suggestMealFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

lateinit var btnexit:Button
lateinit var RandomMealName:TextView
lateinit var RandomMealCategory:TextView
lateinit var RandomMealArea:TextView
lateinit var RandomMealImg: ImageView
lateinit var imgUrl:String
lateinit var viewModel: SuggestfragViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_suggest_meal, container, false)
        btnexit=view.findViewById(R.id.exitbtn)
        setUpViewModel()
        viewModel.GetRandomMeal()

        RandomMealImg=view.findViewById(R.id.RandomMealImg)
        RandomMealName=view.findViewById( R.id.RandomMealName)
        RandomMealCategory=view.findViewById(R.id.RandoCategroy)
        RandomMealArea=view.findViewById(R.id.RandomMealArea)

        btnexit.setOnClickListener({
            val manager = parentFragmentManager
            val transaction = manager.beginTransaction()
            transaction.remove(this)
            transaction.commit()        })
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@suggestMealFragment.requireContext(),value, Toast.LENGTH_SHORT).show()
            }
        }
        val observerRandomMeal: Observer<Response<RandomMeal>> = object : Observer<Response<RandomMeal>> {
            override fun onChanged(value: Response<RandomMeal>) {
                RandomMealName.text=value.body()?.Randomeals?.get(0)?.strMeal
                RandomMealCategory.text=value.body()?.Randomeals?.get(0)?.strCategory
                RandomMealArea.text=value.body()?.Randomeals?.get(0)?.strArea
                imgUrl=value.body()?.Randomeals?.get(0)?.strMealThumb.toString()


                Glide.with(this@suggestMealFragment)
                    .load(imgUrl)
                    .centerCrop()
                    .into(RandomMealImg)
            }
        }
        RandomMealImg.setOnClickListener({
            val outIntent = Intent (this@suggestMealFragment.requireContext() ,   MealDescription::class.java)
            outIntent.putExtra("MealName" ,  RandomMealName.text)
            outIntent.putExtra("MealImg",imgUrl)
           startActivity(outIntent)
        })
        viewModel.msg.observe(viewLifecycleOwner, observerMsg)
        viewModel.RandomMeal.observe(viewLifecycleOwner, observerRandomMeal)


     /*   viewLifecycleOwner.lifecycleScope.launch {
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
            }*/

        RandomMealName.text



        return view
    }
   private fun setUpViewModel(){
       val retrofit = RetroBuilder.service
       val dao = MealsDatabase.getinstanceDatabase(this@suggestMealFragment.requireContext()).getmealsDao()
       val factory= SuggestFregFactory(dao , retrofit)
       viewModel= ViewModelProvider(this,factory).get(SuggestfragViewModel::class.java)

   }


}