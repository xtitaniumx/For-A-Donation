package com.example.donate.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.donate.R
import com.example.donate.databinding.ItemProfileOptionBinding
import com.example.donate.presentation.model.ProfileOptionItem

class ProfileOptionAdapter(private val list: List<ProfileOptionItem>, private val listener: OnClickListener) : RecyclerView.Adapter<ProfileOptionAdapter.Holder>() {
    interface OnClickListener {
        fun onProfileOptionClick(item: ProfileOptionItem)
    }

    class Holder(itemView: View, private val listener: OnClickListener) : ViewHolder(itemView) {
        private val binding = ItemProfileOptionBinding.bind(itemView)
        private lateinit var profileOptionItem: ProfileOptionItem

        init {
            binding.root.setOnClickListener {
                listener.onProfileOptionClick(item = profileOptionItem)
            }
        }

        fun bind(item: ProfileOptionItem) = with(binding) {
            profileOptionItem = item
            imageProfileOption.setImageResource(item.optionImage)
            textProfileOptionName.text = item.optionName
            if (item.optionDesc.isEmpty() && textProfileOptionDesc.visibility == View.VISIBLE) {
                textProfileOptionDesc.visibility = View.GONE
            }
            else
                textProfileOptionDesc.text = item.optionDesc
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile_option, parent, false)
        return Holder(view, listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}