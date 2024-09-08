package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.Model.Database.MealsDatabase
import com.example.myapplication.Model.netwrok.RetroBuilder
import com.example.myapplication.ViewModel.MainActivityViewModel
import com.example.myapplication.ViewModel.MainFactory
import com.example.myapplication.view.adapters.CategoriesAdapter
import com.example.myapplication.view.adapters.CountiresAdapter
import com.example.myapplication.view.suggestMealFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth


class FirstFragment : Fragment() {
    lateinit var favbtn: Button
    lateinit var btnsuggest: Button
    lateinit var accountbtn: Button
    lateinit var searchbtn: Button
    lateinit var categoriesAdapter: CategoriesAdapter
    lateinit var countriesAdapter: CountiresAdapter
    lateinit var viewModel: MainActivityViewModel
    lateinit var recyclerViewCategrories: RecyclerView
    lateinit var recyclerViewCountries: RecyclerView
    lateinit var textView: TextView
    lateinit var swip : SwipeRefreshLayout
    lateinit var userID: String
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)
        var textView = view.findViewById<TextView>(R.id.textviewmsg)
        var userNameString = arguments?.getString("USER_NAME")
        if (userNameString == "guest") {
            textView.text = "Hello guest!"
        }
        else{
            textView.text = "Welcome, $userNameString"
        }

        setUpViewModel()
        viewModel.getCategories()
        viewModel.getCountries()



        // RecyclerView setup
                val recyclerViewCategrories = view.findViewById<RecyclerView>(R.id.recyclerViewTxt)
                categoriesAdapter = CategoriesAdapter(ArrayList(), requireContext())
                recyclerViewCategrories?.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                recyclerViewCategrories?.adapter = categoriesAdapter

                val recyclerViewCountries = view.findViewById<RecyclerView>(R.id.recyclerViewCountries)
                countriesAdapter = CountiresAdapter(ArrayList(), requireContext())
                recyclerViewCountries.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                recyclerViewCountries.adapter = countriesAdapter


                // Observe ViewModel data
                viewModel.Category.observe(viewLifecycleOwner, Observer { categories ->
                    categoriesAdapter.categorylist = categories
                    categoriesAdapter.notifyDataSetChanged()
                })

                viewModel.Country.observe(viewLifecycleOwner, Observer { countries ->
                    countriesAdapter.Countireslist = countries
                    countriesAdapter.notifyDataSetChanged()
                })
        btnsuggest = view?.findViewById<Button>(R.id.suggestbtn)!!
        btnsuggest.setOnClickListener({
            val dynamicFragment = suggestMealFragment()
            val manager = parentFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.suggestFragment, dynamicFragment)
            transaction.commit()
        })


        return view
    }

    private fun setUpViewModel() {
        val retrofit = RetroBuilder.service
        val dao = MealsDatabase.getinstanceDatabase(this@FirstFragment.requireContext()).getmealsDao()
        val factory= MainFactory(dao , retrofit)
        viewModel= ViewModelProvider(this,factory).get(MainActivityViewModel::class.java)
    }


}