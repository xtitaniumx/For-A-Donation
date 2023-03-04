package com.example.donate.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.R
import com.example.donate.databinding.FragmentTasksBinding
import com.example.donate.domain.model.TaskItem
import com.example.donate.presentation.adapter.TaskAdapter

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private val taskAdapter by lazy { TaskAdapter() }

    companion object {
        @JvmStatic
        fun newInstance() = TasksFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTasksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        textGoalProgress.text = getString(R.string.goal_percentage_pattern, progressGoal.progress)

        listTasks.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = taskAdapter
        }

        // Здесь будет потом получение данных с сервера, через observer
        taskAdapter.submitList(listOf(
            TaskItem(name = "Header 1", desc = "Desc 1", R.drawable.ic_tasks),
            TaskItem(name = "Header 2", desc = "Desc 2", R.drawable.ic_tasks),
            TaskItem(name = "Header 3", desc = "Desc 3", R.drawable.ic_tasks)
        ))
    }
}