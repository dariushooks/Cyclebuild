package com.rookieandroid.cyclebuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class MainActivity : AppCompatActivity()
{
    private lateinit var bottomNavigationView : BottomNavigationView
    private val homeViewModel : HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        homeViewModel.getToggle().observe(this) {toggle ->
            if(toggle)
                bottomNavigationView.visibility = View.VISIBLE
            else
                bottomNavigationView.visibility = View.GONE
        }
    }
}