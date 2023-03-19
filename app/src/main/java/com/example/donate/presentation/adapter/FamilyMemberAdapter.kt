package com.example.donate.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.donate.R
import com.example.donate.databinding.ItemFamilyMemberBinding
import com.example.donate.domain.model.FamilyMemberItem
import com.example.donate.domain.model.TaskItem

class FamilyMemberAdapter(
    private val listener: OnClickListener,
    private val childAdaptersList: Map<Int, FamilyMemberTaskAdapter>
) : ListAdapter<FamilyMemberItem, FamilyMemberAdapter.Holder>(Comparator()) {

    fun submitNestedList(list: List<TaskItem>, position: Int) {
        childAdaptersList[position]?.submitList(list)
    }

    fun currentNestedList(position: Int): List<TaskItem>? {
        return childAdaptersList[position]?.currentList
    }

    interface OnClickListener {
        fun onAddFamilyMemberTaskClick(item: FamilyMemberItem)
    }

    class Holder(itemView: View, listener: OnClickListener, private val childAdaptersList: Map<Int, FamilyMemberTaskAdapter>) : ViewHolder(itemView) {
        private val binding = ItemFamilyMemberBinding.bind(itemView)
        private val context = itemView.context
        private lateinit var familyMemberItem: FamilyMemberItem

        init {
            binding.buttonAddTask.setOnClickListener {
                listener.onAddFamilyMemberTaskClick(item = familyMemberItem)

                if (!binding.textFamilyMemberTasksEmpty.isGone) {
                    binding.textFamilyMemberTasksEmpty.isGone = true
                    binding.listFamilyMemberTasks.isGone = false
                }
            }
        }

        fun bind(item: FamilyMemberItem) = with(binding) {
            familyMemberItem = item
            textFamilyMemberRole.text = context.resources.getStringArray(R.array.family_roles)[item.role]
            textFamilyMemberGoal.text = ""
            //if (listFamilyMemberTasks.adapter == null) {
                listFamilyMemberTasks.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = childAdaptersList[item.role]
                }
            //}
        }
    }

    class Comparator : DiffUtil.ItemCallback<FamilyMemberItem>() {
        override fun areItemsTheSame(oldItem: FamilyMemberItem, newItem: FamilyMemberItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FamilyMemberItem, newItem: FamilyMemberItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_family_member, parent, false)
        return Holder(view, listener, childAdaptersList)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}