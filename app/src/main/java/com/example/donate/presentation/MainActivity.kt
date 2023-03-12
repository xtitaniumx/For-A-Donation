package com.example.donate.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.donate.R
import com.example.donate.databinding.ActivityMainBinding
import com.example.donate.presentation.util.ArgumentConstants
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val fragmentList by lazy {
        listOf(
            TasksFragment.newInstance(
                userId = intent.getStringExtra(ArgumentConstants.USER_ID),
                familyId = intent.getStringExtra(ArgumentConstants.FAMILY_ID)
            )
        )
    }
    private var activeFragment: Fragment? = null

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
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun openFragment(fragment: Fragment) {
        if (activeFragment != null && activeFragment != fragment) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.containerMain, fragment)
            transaction.commit()
        }
        activeFragment = fragment
    }
}