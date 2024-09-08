package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.R
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.view.adapters.MealAdapter

class MealByCategoryFragment : Fragment(), OnButtonClick {
    lateinit var categoryView: TextView
    lateinit var homebtn: Button
    lateinit var favbtn: Button
    lateinit var accountbtn: Button
    lateinit var adapter: MealAdapter
    lateinit var viewModel: FilterViewModel
    lateinit var mealsRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
         val view= inflater.inflate(R.layout.fragment_meal_by_category, container, false)
        setUpViewModel()

        categoryView= view.findViewById(R.id.CategoryView)

        val categoryName = arguments?.getString("categoryName")
        categoryView.text=categoryName

        val categoryString:String= categoryView.text.toString()

        val onButtonClick = this

        viewModel.getMealsByCategory(categoryString)
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@MealByCategoryFragment.requireContext(),value, Toast.LENGTH_SHORT).show()
            }
        }
        val observerMeals: Observer<ArrayList<Meals>> = object : Observer<ArrayList<Meals>> {
            override fun onChanged(value: ArrayList<Meals>) {
                adapter.meallist = value
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.msg.observe(viewLifecycleOwner, observerMsg)
        viewModel.Meals.observe(viewLifecycleOwner, observerMeals)


        mealsRecyclerView = view.findViewById(R.id.mealsRecyclerView)
        adapter = MealAdapter(ArrayList(), this@MealByCategoryFragment.requireContext(), onButtonClick)
        mealsRecyclerView.layoutManager= LinearLayoutManager(this@MealByCategoryFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
        mealsRecyclerView.adapter =adapter


        return view
    }
    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this@MealByCategoryFragment.requireContext()).getmealsDao()
        val factory= FilterFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FilterViewModel::class.java)
    }

    override fun favbtnclick(meal: Meals) {
        viewModel.addMealToFav(meal)
    }

    override fun deletebtnclick(meal: Meals) {
        viewModel.deleteMealFromFav(meal)
    }

}