package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MealByAreaFragment : Fragment() , OnButtonClick {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MealAdapter
    lateinit var viewModel: FilterViewModel
    lateinit var areaTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_meal_by_area, container, false)
        val areaName = arguments?.getString("AreaName")
        val areaString=areaName.toString()
        areaTextView=view.findViewById(R.id.areaTextView)
        areaTextView.text=areaName

        setUpViewModel()

        viewModel.getMealsByArea(areaString)
        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@MealByAreaFragment.requireContext(),value, Toast.LENGTH_SHORT).show()
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


        recyclerView = view.findViewById(R.id.mealRecycler)
        adapter = MealAdapter(ArrayList(), this@MealByAreaFragment.requireContext(), this)
        recyclerView.layoutManager= LinearLayoutManager(this@MealByAreaFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        return view
    }
    private fun setUpViewModel(){
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this@MealByAreaFragment.requireContext()).getmealsDao()
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