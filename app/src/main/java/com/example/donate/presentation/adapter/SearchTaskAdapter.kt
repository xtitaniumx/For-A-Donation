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

class SearchTaskAdapter(private val listener: OnClickListener) : ListAdapter<TaskItem, SearchTaskAdapter.Holder>(Comparator()) {
    interface OnClickListener {
        fun onTaskClick(item: TaskItem)
    }

    class Holder(itemView: View, private val listener: OnClickListener) : ViewHolder(itemView) {
        private val binding = ItemTaskBinding.bind(itemView)
        private lateinit var taskItem: TaskItem

        init {
            binding.root.setOnClickListener {
                listener.onTaskClick(item = taskItem)
            }
        }

        fun bind(item: TaskItem) = with(binding) {
            taskItem = item
            textTaskName.text = item.name
            //textTaskDesc.text = item.desc
            //imageTask.setImageResource(item.icon)
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
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
}