package com.example.dailyplanner.presentation.main_screen

import androidx.recyclerview.widget.DiffUtil
import com.example.dailyplanner.domain.model.TaskItem

class DiffUtilItemCallbackMain: DiffUtil.ItemCallback<TaskItem>() {

    override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
        return oldItem == newItem
    }

}