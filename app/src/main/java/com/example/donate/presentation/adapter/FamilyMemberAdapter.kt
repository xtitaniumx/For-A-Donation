package com.example.donate.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.donate.R
import com.example.donate.databinding.ItemFamilyMemberBinding
import com.example.donate.domain.model.FamilyMemberItem
import com.example.donate.domain.model.TaskItem
import com.google.android.material.divider.MaterialDividerItemDecoration

class FamilyMemberAdapter(
    private val listener: OnClickListener,
    private val childAdaptersList: Map<String, FamilyMemberTaskAdapter>
) : ListAdapter<FamilyMemberItem, FamilyMemberAdapter.Holder>(Comparator()) {

    interface OnClickListener {
        fun onShowCategoriesMenuClick(view: View, familyMemberId: String)

        fun onAddFamilyMemberTaskClick(item: FamilyMemberItem)
    }

    fun submitNestedList(list: List<TaskItem>?, familyMemberId: String) {
        childAdaptersList[familyMemberId]?.submitList(list)
    }

    class Holder(itemView: View, listener: OnClickListener, private val childAdaptersList: Map<String, FamilyMemberTaskAdapter>) : ViewHolder(itemView) {
        private val binding = ItemFamilyMemberBinding.bind(itemView)
        private val context = itemView.context
        private lateinit var familyMemberItem: FamilyMemberItem

        init {
            binding.buttonShowCategories.setOnClickListener {
                listener.onShowCategoriesMenuClick(view = it, familyMemberId = familyMemberItem.id)
            }
            binding.buttonAddTask.setOnClickListener {
                listener.onAddFamilyMemberTaskClick(item = familyMemberItem)
            }
        }

        fun bind(item: FamilyMemberItem) = with(binding) {
            familyMemberItem = item
            textFamilyMemberRole.text = context.resources.getStringArray(R.array.family_roles)[item.role]
            textFamilyMemberGoal.text = "Хочет: путёвку в Египет"

            if (listFamilyMemberTasks.adapter == null) {
                val divider = MaterialDividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                listFamilyMemberTasks.apply {
                    layoutManager = LinearLayoutManager(context)
                    addItemDecoration(divider)
                    adapter = childAdaptersList[item.id]
                }
            }
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