package com.sachin.milkdistributor

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        initToolbar()


    }

    private fun initToolbar() {

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val navHostFragment = nav_host_fragment as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph, intent.extras)

        setupActionBarWithNavController(navController)
    }





}