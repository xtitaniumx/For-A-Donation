package com.example.donate.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.R
import com.example.donate.databinding.ActivitySearchBinding
import com.example.donate.domain.model.TaskItem
import com.example.donate.presentation.adapter.TaskAdapter
import com.example.donate.presentation.util.addChip
import com.example.donate.presentation.util.showTaskInfo
import com.example.donate.presentation.vm.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity(), TaskAdapter.OnClickListener {
    private val vm by viewModel<SearchViewModel>()
    private lateinit var binding: ActivitySearchBinding
    private val tasksAdapter by lazy { TaskAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        init()
        setContentView(binding.root)
    }

    private fun init() = with(binding) {
        val taskCategories = resources.getStringArray(R.array.task_categories)
        taskCategories.forEach {
            chipGroupFilters.addView(addChip(taskCategories.indexOf(it), it))
        }
        chipGroupFilters.check(0)

        listTasks.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = tasksAdapter
        }

        chipGroupFilters.setOnCheckedStateChangeListener { _, _ ->
            vm.searchTasks(
                query = viewSearch.editTextSearchTasks.text.toString(),
                category = chipGroupFilters.checkedChipId
            )
        }

        viewSearch.editTextSearchTasks.addTextChangedListener {
            vm.searchTasks(
                query = it.toString(),
                category = chipGroupFilters.checkedChipId
            )
        }

        vm.tasksListLive.observe(this@SearchActivity) {
            if (vm.tasksListLive.value.isNullOrEmpty())
                layoutEmptySearch.visibility = View.VISIBLE
            else
                layoutEmptySearch.visibility = View.INVISIBLE
            Log.d("info", "ChipID: ${chipGroupFilters.checkedChipId}")
            tasksAdapter.submitList(it)
        }
    }

    override fun onTaskClick(item: TaskItem) {
        showTaskInfo(taskItem = item)
    }
}