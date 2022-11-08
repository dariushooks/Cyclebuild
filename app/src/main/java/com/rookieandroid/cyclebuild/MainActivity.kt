package com.rookieandroid.cyclebuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rookieandroid.cyclebuild.architecture.HomeViewModel

class MainActivity : AppCompatActivity()
{
    private lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        bottomNavigationView = findViewById(R.id.bottom_nav)
        bottomNavigationView.setOnItemReselectedListener { item ->
            navController.popBackStack(item.itemId, false)
        }

        navController.addOnDestinationChangedListener(listener = { controller, destination, arguments ->
            if(destination.id == R.id.fragment_sign_in || destination.id == R.id.fragment_sign_up)
                bottomNavigationView.visibility = View.GONE
            else
                bottomNavigationView.visibility = View.VISIBLE
        })

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }
}