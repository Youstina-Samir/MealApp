package com.example.myapplication.view

import android.os.Bundle
import android.os.Handler
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.R
import com.example.myapplication.ViewModel.MainActivityViewModel
import com.example.myapplication.view.adapters.CategoriesAdapter
import com.example.myapplication.view.adapters.CountiresAdapter
import com.example.myapplication.view.signIn.AccountFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class NewMainActivity : AppCompatActivity() {
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
    lateinit var userNameString: String
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    val firstFragment = FirstFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)
        enableEdgeToEdge()
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        mAuth = FirebaseAuth.getInstance()
        val gso = getGoogleSignInOptions()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        mAuth.addAuthStateListener { auth ->
            Handler(mainLooper).postDelayed({
                val user = auth.currentUser
                if (user != null) {
                    val userName = user.displayName
                    userID= FirebaseAuth.getInstance().currentUser!!.uid
                    //textView.text = "Welcome, $userName"
                    userNameString = userName.toString()
                    setCurrentFragment(firstFragment,userNameString)
                } else {
                    userNameString="guest"
                    setCurrentFragment(firstFragment,userNameString)}
                /*else {
                    // No signed-in user, start SignInActivity
                    val signIntent = Intent(this, SignActivity::class.java)
                    startActivity(signIntent)
                }*/
            },  500)}


        val searchFragment = SearchFragment()
        val accountFragment = AccountFragment()
        val favFragment = FavFragment()


        bottomNavigationView.setOnNavigationItemSelectedListener {item->
            when (item.itemId) {
                R.id.navigation_home -> setCurrentFragment(firstFragment,userNameString)
                 R.id.navigation_search ->setCurrentFragment(searchFragment)
                R.id.navigation_account ->setCurrentFragment(accountFragment)
                R.id.navigation_fav ->setCurrentFragment(favFragment)

            }
            true
        }


    }

    private fun setCurrentFragment(fragment: Fragment, userName: String) =
        supportFragmentManager.beginTransaction().apply {
            val bundle = Bundle().apply {
                putString("USER_NAME", userName)  // Pass userId to the bundle
            }
            fragment.arguments = bundle
            replace(R.id.flFragment, fragment)
            commitAllowingStateLoss()
        }
    private fun setCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {

            replace(R.id.flFragment, fragment)
            commitAllowingStateLoss()        }



    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }
}

