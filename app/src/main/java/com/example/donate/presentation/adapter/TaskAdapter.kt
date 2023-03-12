package com.example.donate.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.donate.R
import com.example.donate.databinding.ItemTaskBinding
import com.example.donate.domain.model.TaskItem

class TaskAdapter : ListAdapter<TaskItem, TaskAdapter.Holder>(Comparator()) {
    class Holder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemTaskBinding.bind(itemView)

        fun bind(item: TaskItem) = with(binding) {
            textTaskName.text = item.name
            textTaskDesc.text = item.description
            if (item.isFinished) imageTask.setImageResource(R.drawable.ic_tasks)
            else imageTask.setImageResource(R.drawable.ic_time)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}