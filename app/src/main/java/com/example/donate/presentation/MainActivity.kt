package com.example.donate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.donate.R
import com.example.donate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        navMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navItemTasks -> {
                    openFragment(TasksFragment.newInstance())
                    return@setOnItemSelectedListener true
                }
                R.id.navItemFamily -> {
                    return@setOnItemSelectedListener true
                }
                R.id.navItemProfile -> {
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerMain, fragment)
        transaction.commit()
    }
}