package com.example.myapplication

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
import com.example.myapplication.ViewModel.FavFactory
import com.example.myapplication.ViewModel.FavViewModel
import com.example.myapplication.view.OnButtonClick
import com.example.myapplication.view.adapters.MealAdapter


class FavFragment : Fragment() , OnButtonClick {
    lateinit var homebtn: Button
    lateinit var accountbtn: Button
    lateinit var favbtn: Button
    lateinit var deletebtn: Button
    lateinit var recycler: RecyclerView
    lateinit var adapter: MealAdapter
    lateinit var viewModel: FavViewModel
    lateinit var nofav: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view=inflater.inflate(R.layout.fragment_fav, container, false)
        setUpViewModel()
        recycler=view.findViewById(R.id.favRecycler)
        nofav=view.findViewById(R.id.nofav)
        adapter= MealAdapter(ArrayList<Meals>(),this.requireContext(),this)
        recycler.layoutManager = LinearLayoutManager(this.requireContext(), LinearLayoutManager.VERTICAL, false)
        recycler.adapter = adapter

        viewModel.getFavMeals()

        val observerMsg: Observer<String> = object : Observer<String> {
            override fun onChanged(value: String) {
                Toast.makeText(this@FavFragment.requireContext(), value, Toast.LENGTH_SHORT).show()

            }
        }
        val observerProducts: Observer<List<Meals>> = object : Observer<List<Meals>> {
            override fun onChanged(value: List<Meals>) {
                if (value.isEmpty()) {
                    nofav.text = "Nothing added to favorites yet"
                    nofav.visibility = View.VISIBLE // Ensure it's visible
                } else {
                    nofav.text = ""
                }
                adapter.meallist = value
                adapter.notifyDataSetChanged()
            }
        }
        viewModel.msg.observe(viewLifecycleOwner, observerMsg)
        viewModel.Meals.observe(viewLifecycleOwner, observerProducts)


        return view
    }
    fun setUpViewModel(){
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this@FavFragment.requireContext()).getmealsDao()
        val factory= FavFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(FavViewModel::class.java)

    }
    override fun favbtnclick(meal: Meals) {
        viewModel.addMealToFav(meal)
    }




    override fun deletebtnclick(meal: Meals) {
        viewModel.deleteMealFromFav(meal)}

}