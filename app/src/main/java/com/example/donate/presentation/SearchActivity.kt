package com.example.donate.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.databinding.ActivitySearchBinding
import com.example.donate.domain.model.TaskItem
import com.example.donate.presentation.adapter.TaskAdapter

class SearchActivity : AppCompatActivity(), TaskAdapter.OnClickListener {
    private lateinit var binding: ActivitySearchBinding
    private val tasksAdapter by lazy { TaskAdapter(this) }
    private val list = ArrayList<TaskItem>()
    private var listNum = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        listTasks.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = tasksAdapter
        }
        /*tasksAdapter.submitList(listOf(
            TaskItem(name = "Header 1", desc = "Desc 1", icon = R.drawable.ic_tasks),
            TaskItem(name = "Header 2", desc = "Desc 2", icon = R.drawable.ic_tasks),
            TaskItem(name = "Header 3", desc = "Desc 3", icon = R.drawable.ic_tasks)
        ))*/
    }

    override fun onTaskClick(item: TaskItem) {

    }
}