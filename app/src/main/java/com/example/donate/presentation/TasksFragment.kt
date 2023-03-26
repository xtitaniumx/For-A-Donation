package com.example.donate.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.R
import com.example.donate.databinding.FragmentTasksBinding
import com.example.donate.domain.model.TaskItem
import com.example.donate.presentation.adapter.TaskAdapter
import com.example.donate.presentation.util.addChip
import com.example.donate.presentation.util.createLoadingDialog
import com.example.donate.presentation.util.showTaskInfo
import com.example.donate.presentation.vm.TasksViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TasksFragment : Fragment(), TaskAdapter.OnClickListener {
    private val vm by viewModel<TasksViewModel>()
    private lateinit var binding: FragmentTasksBinding
    private lateinit var loadingDialog: AlertDialog
    private val taskAdapter by lazy { TaskAdapter(this) }

    companion object {
        @JvmStatic
        fun newInstance() = TasksFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = requireActivity().createLoadingDialog("Получение списка задач").show()
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
        textGoalProgress.text = (20).toString()
        textGoalTotal.text = getString(R.string.goal_progress_pattern, 30)
        progressGoal.secondaryProgress = 20 / 30 * 100

        val weekDays = resources.getStringArray(R.array.weekdays)
        weekDays.forEach {
            chipGroupWeekDay.addView(requireActivity().addChip(weekDays.indexOf(it), it))
        }
        chipGroupWeekDay.check(0)

        viewSearch.editTextSearchTasks.keyListener = null
        viewSearch.editTextSearchTasks.isFocusable = false

        viewSearch.editTextSearchTasks.setOnClickListener {
            val intent = Intent(requireActivity(), SearchActivity::class.java)
            startActivity(intent)
        }

        listTasks.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = taskAdapter
        }

        vm.tasksListLive.observe(viewLifecycleOwner) {
            taskAdapter.submitList(it)
            loadingDialog.dismiss()
        }

        fabNewTask.setOnClickListener {
            val intent = Intent(requireActivity(), NewTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onTaskClick(item: TaskItem) {
        requireActivity().showTaskInfo(taskItem = item)
    }
}