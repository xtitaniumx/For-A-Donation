package com.example.donate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.donate.R
import com.example.donate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentList by lazy {
        listOf(
            TasksFragment.newInstance(),
            FamilyFragment.newInstance(),
            ProfileFragment.newInstance()
        )
    }
    private var activeFragment = fragmentList[0]

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
                    openFragment(fragmentList[0])
                }
                R.id.navItemFamily -> {
                    openFragment(fragmentList[1])
                }
                R.id.navItemProfile -> {
                    openFragment(fragmentList[2])
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun openFragment(fragment: Fragment) {
        if (activeFragment != fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerMain, fragment)
            transaction.commit()
        }
        activeFragment = fragment
    }
}