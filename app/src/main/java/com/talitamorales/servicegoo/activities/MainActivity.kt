package com.talitamorales.servicegoo.activities

import android.app.SearchManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.fragments.HomeFragment
import com.talitamorales.servicegoo.fragments.ProfileFragment
import com.talitamorales.servicegoo.fragments.SearchFragment


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        configureWindowListener()
        configureTabBar()
        supportActionBar?.title = "ServiceGoo"
    }

    private fun configureWindowListener() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configureTabBar() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_Fragment, HomeFragment())
            .commit()
        bottomNavigationView.setOnItemSelectedListener { item ->
            val selectedFragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_search -> SearchFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            selectedFragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container_Fragment, it)
                    .commit()
                true
            } ?: false
        }
    }
}