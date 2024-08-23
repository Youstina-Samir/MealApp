package com.example.myapplication.view

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.MediaController
import android.widget.TextView
import android.widget.Toast
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.Model.Database.MealsDao
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.MealDescriptionArray
import com.example.myapplication.Model.RandomMeal
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.Model.netwrok.SimpleService
import com.example.myapplication.R
import com.example.myapplication.ViewModel.MealDescriptionFragmentViewModel
import com.example.myapplication.ViewModel.SuggestFregFactory
import com.example.myapplication.ViewModel.SuggestfragViewModel
import retrofit2.Response


class IngredientsFragment : Fragment() {
    lateinit var viewModel: MealDescriptionFragmentViewModel
    lateinit var ingredients_text1: TextView
    lateinit var ingredients_text2: TextView
    lateinit var ingredients_text3: TextView
    lateinit var ingredients_text4: TextView
    lateinit var ingredients_text5: TextView
    lateinit var ingredients_text6: TextView
    lateinit var ingredients_text7: TextView
    lateinit var ingredients_text8: TextView
    lateinit var ingredients_text9: TextView
    lateinit var ingredients_text10: TextView
    lateinit var measure_text1: TextView
    lateinit var measure_text2: TextView
    lateinit var measure_text3: TextView
    lateinit var measure_text4: TextView
    lateinit var measure_text5: TextView
    lateinit var measure_text6: TextView
    lateinit var measure_text7: TextView
    lateinit var measure_text8: TextView
    lateinit var measure_text9: TextView
    lateinit var measure_text10: TextView



    private lateinit var mealName: String
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpViewModel()
        mealName = arguments?.getString("mealName") ?: "Default Meal Name"

        viewModel.getMealDetail(mealName)
        // Inflate the layout for this fragmen
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@IngredientsFragment.requireContext(), value, Toast.LENGTH_SHORT)
                    .show()
            }
        }
        val observerMealDetail: Observer<Response<MealDescriptionArray>> = object :
            Observer<Response<MealDescriptionArray>> {
            override fun onChanged(value: Response<MealDescriptionArray>) {
                ingredients_text1.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient1
                ingredients_text2.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient2
                ingredients_text3.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient3
                ingredients_text4.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient4
                ingredients_text5.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient5
                ingredients_text6.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient6
                ingredients_text7.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient7
                ingredients_text8.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient8
                ingredients_text9.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient9
                ingredients_text10.text = value.body()?.mealsDescriptionArray?.get(0)?.strIngredient10
                measure_text1.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure1
                measure_text2.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure2
                measure_text3.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure3
                measure_text4.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure4
                measure_text5.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure5
                measure_text6.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure6
                measure_text7.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure7
                measure_text8.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure8
                measure_text9.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure9
                measure_text10.text = value.body()?.mealsDescriptionArray?.get(0)?.strMeasure10

            }
        }
        viewModel.msg.observe(viewLifecycleOwner, observerMsg)
        viewModel.mealDetail.observe(viewLifecycleOwner, observerMealDetail)


        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)
        ingredients_text1 = view.findViewById<TextView>(R.id.ingredients_text1)
        ingredients_text2 = view.findViewById<TextView>(R.id.ingredients_text2)
        ingredients_text3 = view.findViewById<TextView>(R.id.ingredients_text3)
        ingredients_text4 = view.findViewById<TextView>(R.id.ingredients_text4)
        ingredients_text5 = view.findViewById<TextView>(R.id.ingredients_text5)
        ingredients_text6 = view.findViewById<TextView>(R.id.ingredients_text6)
        ingredients_text7 = view.findViewById<TextView>(R.id.ingredients_text7)
        ingredients_text8 = view.findViewById<TextView>(R.id.ingredients_text8)
        ingredients_text9 = view.findViewById<TextView>(R.id.ingredients_text9)
        ingredients_text10 = view.findViewById<TextView>(R.id.ingredients_text10)
        measure_text1 = view.findViewById<TextView>(R.id.measure_text1)
        measure_text2 = view.findViewById<TextView>(R.id.measure_text2)
        measure_text3 = view.findViewById<TextView>(R.id.measure_text3)
        measure_text4 = view.findViewById<TextView>(R.id.measure_text4)
        measure_text5 = view.findViewById<TextView>(R.id.measure_text5)
        measure_text6 = view.findViewById<TextView>(R.id.measure_text6)
        measure_text7 = view.findViewById<TextView>(R.id.measure_text7)
        measure_text8 = view.findViewById<TextView>(R.id.measure_text8)
        measure_text9 = view.findViewById<TextView>(R.id.measure_text9)
        measure_text10 = view.findViewById<TextView>(R.id.measure_text10)


        return view
    }

    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this@IngredientsFragment.requireContext())
            .getmealsDao()
        val factory = DetailsFregFactory(dao, retrofit)
        viewModel = ViewModelProvider(this, factory).get(MealDescriptionFragmentViewModel::class.java)

    }
}
    // InstructionsFragment.kt
    class InstructionsFragment : Fragment() {
        lateinit var viewModel: MealDescriptionFragmentViewModel
         lateinit var mealName: String
         lateinit var videoUrl:String
         lateinit var webView: WebView
         lateinit var instructionsText:TextView

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.fragment_instructions, container, false)
            setUpViewModel()
            mealName = arguments?.getString("mealName") ?: "Default Meal Name"
            viewModel.getMealDetail(mealName)
            // Inflate the layout for this fragmen
            val observerMsg: Observer<String> = object : Observer<String> {
                override fun onChanged(value: String) {
                    Toast.makeText(this@InstructionsFragment.requireContext(), value, Toast.LENGTH_SHORT)
                        .show()
                }
            }
            val observerMealDetail: Observer<Response<MealDescriptionArray>> = object :
                Observer<Response<MealDescriptionArray>> {
                override fun onChanged(value: Response<MealDescriptionArray>) {
                    videoUrl=value.body()?.mealsDescriptionArray?.get(0)?.strYoutube.toString()
                    webView.settings.javaScriptEnabled = true // Enable JavaScript
                    webView.webViewClient = WebViewClient()
                    webView.loadUrl(videoUrl)

                    instructionsText.text = value.body()?.mealsDescriptionArray?.get(0)?.strInstructions


                }
            }
            viewModel.msg.observe(viewLifecycleOwner, observerMsg)
            viewModel.mealDetail.observe(viewLifecycleOwner, observerMealDetail)
             instructionsText = view.findViewById(R.id.instruction_text1)

            webView=view.findViewById(R.id.webView)
            // Ensure links open in WebView


            return view
        }
        private fun setUpViewModel() {
            val retrofit = RetroBuilder.service
            val dao = MealsDatabase.getinstanceDatabase(this@InstructionsFragment.requireContext())
                .getmealsDao()
            val factory = DetailsFregFactory(dao, retrofit)
            viewModel = ViewModelProvider(this, factory).get(MealDescriptionFragmentViewModel::class.java)

        }
    }

class DetailsFregFactory   (private val dao: MealsDao, private val retrofit: SimpleService): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealDescriptionFragmentViewModel::class.java)) {
            return MealDescriptionFragmentViewModel(dao, retrofit) as T}
        else
            throw IllegalArgumentException("Unknown ViewModel")
    }

}