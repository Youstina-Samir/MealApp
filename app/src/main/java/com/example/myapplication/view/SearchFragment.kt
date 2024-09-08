package com.example.myapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.MealDescription
import com.example.myapplication.Model.Meals
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.R
import com.example.myapplication.ViewModel.FilterFactory
import com.example.myapplication.ViewModel.FilterViewModel
import com.example.myapplication.view.adapters.MealAdapter
import com.example.myapplication.view.adapters.MealDescriptionAdapter


class SearchFragment : Fragment(), OnButtonClick {
    lateinit var viewModel: FilterViewModel
    lateinit var RecyclerView: RecyclerView
    lateinit var adapter: MealAdapter
    lateinit var adapter2 : MealDescriptionAdapter
    lateinit var searchView: SearchView
    lateinit var spinner: Spinner
    lateinit var SpinnerChoice:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        setUpViewModel()

        val onButtonClick = this

        searchView =view.findViewById<SearchView>(R.id.searchView)
        spinner= view.findViewById<Spinner>(R.id.spinner)
        SpinnerChoice=""

        var spinnerOptions = arrayOf("Meal name","Category", "Nationality", "Main ingredients")
        spinner.adapter = ArrayAdapter<String>(this@SearchFragment.requireContext(), android.R.layout.simple_spinner_item, spinnerOptions)
        spinner.onItemSelectedListener= object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                SpinnerChoice="Meal name"
                setAdapterBasedOnChoice()

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                SpinnerChoice=spinnerOptions[position]
                setAdapterBasedOnChoice()
            }
        }
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (SpinnerChoice == "Meal name") {
                    viewModel.getMealsByName(newText.toString())
                    adapter2.notifyDataSetChanged()
                }
                else if (SpinnerChoice == "Nationality") {
                    viewModel.getMealsByArea(newText.toString())
                    //adapter.notifyDataSetChanged()
                }
                else if (SpinnerChoice == "Category") {
                    viewModel.getMealsByCategory(newText.toString())
                } else if (SpinnerChoice == "Main ingredients") {
                    viewModel.getMealsByIngredient(newText.toString())
                }

                return true
            }

        })




        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@SearchFragment.requireContext(),value, Toast.LENGTH_SHORT).show()
            }
        }
        val observerMeals: Observer<ArrayList<Meals>> = object : Observer<ArrayList<Meals>> {
            override fun onChanged(value: ArrayList<Meals>) {
                adapter.meallist = value
                adapter.notifyDataSetChanged()
            }
        }
        val observerMeals2: Observer<ArrayList<MealDescription>> = object :
            Observer<ArrayList<MealDescription>> {
            override fun onChanged(value: ArrayList<MealDescription>) {
                adapter2.meallist = value
                adapter2.notifyDataSetChanged()
            }
        }
        viewModel.msg.observe(viewLifecycleOwner, observerMsg)
        viewModel.Meals.observe(viewLifecycleOwner, observerMeals)
        viewModel.Meals2.observe(viewLifecycleOwner, observerMeals2)

        RecyclerView= view.findViewById(R.id.RecyclerViewerSearch)
        RecyclerView.layoutManager= LinearLayoutManager(this@SearchFragment.requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter= MealAdapter(arrayListOf(), this@SearchFragment.requireContext() ,this)
        adapter2= MealDescriptionAdapter(arrayListOf(), this@SearchFragment.requireContext() ,this)

        return view
    }

    private fun setAdapterBasedOnChoice() {
        if(SpinnerChoice=="Meal name")
        { RecyclerView.adapter=adapter2}
        else
        {  RecyclerView.adapter=adapter}
    }
    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this@SearchFragment.requireContext()).getmealsDao()
        val factory= FilterFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FilterViewModel::class.java)
    }

    override fun favbtnclick(meal: Meals) {
        viewModel.addMealToFav(meal)
    }




    override fun deletebtnclick(meal: Meals) {
        viewModel.deleteMealFromFav(meal)    }
}