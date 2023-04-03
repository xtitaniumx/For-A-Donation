package com.example.donate.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.donate.R
import com.example.donate.databinding.ItemFamilyMemberTaskBinding
import com.example.donate.domain.model.TaskItem

class FamilyMemberTaskAdapter(private val listener: OnChildClickListener) : ListAdapter<TaskItem, FamilyMemberTaskAdapter.Holder>(Comparator()) {
    interface OnChildClickListener {
        fun onFamilyMemberTaskClick(item: TaskItem)
    }

    class Holder(itemView: View, private val listener: OnChildClickListener) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemFamilyMemberTaskBinding.bind(itemView)
        private lateinit var taskItem: TaskItem
        private lateinit var popupMenu: PopupMenu

        init {
            binding.root.setOnClickListener {
                listener.onFamilyMemberTaskClick(item = taskItem)
            }
        }

        fun bind(item: TaskItem) = with(binding) {
            taskItem = item
            textTaskName.text = item.name
            textTaskDesc.text = item.description
        }
    }

    class Comparator : DiffUtil.ItemCallback<TaskItem>() {
        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_family_member_task, parent, false)
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}