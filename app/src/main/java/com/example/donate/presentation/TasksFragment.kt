package com.example.donate.presentation

import android.content.Intent
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
import com.example.donate.presentation.util.ArgumentConstants
import com.example.donate.presentation.vm.TasksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment() {
    private val vm by viewModel<TasksViewModel>()
    private lateinit var binding: FragmentTasksBinding
    private val taskAdapter by lazy { TaskAdapter() }
    private var userId: String? = null
    private var familyId: String? = null

    companion object {
        @JvmStatic
        fun newInstance(userId: String?, familyId: String?) = TasksFragment().apply {
            arguments = Bundle().apply {
                putString(ArgumentConstants.USER_ID, userId)
                putString(ArgumentConstants.FAMILY_ID, familyId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            userId = it.getString(ArgumentConstants.USER_ID)
            familyId = it.getString(ArgumentConstants.FAMILY_ID)
        }
        vm.getAllTasks()
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
        viewSearch.editTextSearchTasks.keyListener = null
        viewSearch.editTextSearchTasks.isFocusable = false
        viewSearch.editTextSearchTasks.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        //textGoalProgress.text = getString(R.string.goal_progress_pattern, progressGoal.progress)

        listTasks.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = taskAdapter
        }

        vm.tasksListLive.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
        }

        fabNewTask.setOnClickListener {
            val intent = Intent(requireActivity(), NewTaskActivity::class.java)
            intent.putExtra(ArgumentConstants.USER_ID, userId)
            intent.putExtra(ArgumentConstants.FAMILY_ID, familyId)
            startActivity(intent)
        }
    }
}