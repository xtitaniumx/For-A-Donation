package com.example.donate.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.MenuRes
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.donate.R
import com.example.donate.databinding.FragmentFamilyBinding
import com.example.donate.domain.model.FamilyMemberItem
import com.example.donate.domain.model.TaskItem
import com.example.donate.presentation.adapter.FamilyMemberAdapter
import com.example.donate.presentation.adapter.FamilyMemberTaskAdapter
import com.example.donate.presentation.util.IntentConstants
import com.example.donate.presentation.util.createLoadingDialog
import com.example.donate.presentation.util.showTaskInfo
import com.example.donate.presentation.vm.FamilyViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FamilyFragment : Fragment(), FamilyMemberAdapter.OnClickListener, FamilyMemberTaskAdapter.OnChildClickListener {
    private val vm by viewModel<FamilyViewModel>()
    private lateinit var binding: FragmentFamilyBinding
    private lateinit var loadingDialog: AlertDialog
    private val familyMemberTaskAdapters by lazy { HashMap<Int, FamilyMemberTaskAdapter>() }
    private val familyMemberAdapter by lazy { FamilyMemberAdapter(this, familyMemberTaskAdapters) }
    private lateinit var startForResult: ActivityResultLauncher<Intent>

    companion object {
        @JvmStatic
        fun newInstance() = FamilyFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = requireActivity().createLoadingDialog("Получение списка семьи").show()
        vm.getFamilyMembers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFamilyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {
        val intent = Intent(requireActivity(), AddFamilyMemberActivity::class.java)

        vm.childFamilyMembersLive.observe(viewLifecycleOwner) { familyMembers ->
            intent.putExtra(IntentConstants.QR_CODE_VALUE, vm.familyIdLive.value)

            if (familyMembers.isNullOrEmpty()) {
                layoutNoChild.visibility = View.VISIBLE
                return@observe
            } else if (layoutNoChild.isVisible) layoutNoChild.visibility = View.INVISIBLE

            familyMembers.forEach {
                familyMemberTaskAdapters[it.role] = FamilyMemberTaskAdapter(this@FamilyFragment)
            }

            listFamily.apply {
                layoutManager = LinearLayoutManager(requireActivity())
                adapter = familyMemberAdapter
            }

            familyMemberAdapter.submitList(familyMembers)
            loadingDialog.dismiss()

            loadingDialog = requireActivity().createLoadingDialog("Получение списка задач для каждого ребенка").show()
            vm.getTasksByFilter(0)
        }

        /*chipGroupFilters.setOnCheckedStateChangeListener { _, _ ->
            vm.getTasksByFilter(chipGroupFilters.checkedChipId)
        }*/

        fabAddFamilyMember.setOnClickListener {
            startActivity(intent)
        }

        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val taskId = result?.data?.getStringExtra(IntentConstants.NEW_TASK_ID)
                taskId?.let { vm.getTaskById(it, vm.memberRoleIdLive.value!!) }
            }
        }

        vm.childTasksLive.observe(viewLifecycleOwner) { childTasks ->
            childTasks?.forEach { task ->
                task.value?.let { familyMemberAdapter.submitNestedList(it, task.key) }
            }

            if (loadingDialog.isShowing) loadingDialog.dismiss()
        }
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {

        fun categoryFromItemId(itemId: Int): Int {
            return when (itemId) {
                R.id.categoryBehavior -> 0
                R.id.categoryHome -> 1
                R.id.categoryStudy -> 2
                R.id.categoryCreative -> 3
                R.id.categorySport -> 4
                else -> 0
            }
        }

        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            // Respond to menu item click.
            vm.getTasksByFilter(categoryFromItemId(menuItem.itemId))
            return@setOnMenuItemClickListener true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    override fun onShowCategoriesMenuClick(view: View) {
        showMenu(view, R.menu.categories_menu)
    }

    override fun onAddFamilyMemberTaskClick(item: FamilyMemberItem) {
        Intent(requireActivity(), NewTaskActivity::class.java).apply {
            vm.setMemberRole(item.role)
            putExtra(IntentConstants.MEMBER_ROLE, item.role)
            startForResult.launch(this)
        }
    }

    override fun onFamilyMemberTaskClick(item: TaskItem) {
        requireActivity().showTaskInfo(taskItem = item)
    }
}