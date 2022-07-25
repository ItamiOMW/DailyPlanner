package com.example.dailyplanner.presentation.main_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.dailyplanner.R
import com.example.dailyplanner.domain.model.TaskItem

class MainAdapter : ListAdapter<TaskItem, MainViewHolder>(DiffUtilItemCallbackMain()) {

    var onTaskItemClickListener: ((taskItem: TaskItem) -> Unit)? = null
    var onTaskItemLongClickListener: ((taskItem: TaskItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val layout = when (viewType) {
            TASK_ITEM_UNFINISHED -> R.layout.task_item
            TASK_ITEM_FINISHED -> R.layout.task_item_done
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val taskItem = currentList[position]
        holder.tvName.text = taskItem.name
        holder.tvPlannedTimeFrom.text = taskItem.timeFrom
        holder.tvPlannedTimeTo.text = taskItem.timeTo
        holder.itemView.setOnClickListener {
            onTaskItemClickListener?.invoke(taskItem)
        }
        holder.itemView.setOnLongClickListener {
            onTaskItemLongClickListener?.invoke(taskItem)
            true
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = currentList[position]
        return when (item.isDone) {
            true -> TASK_ITEM_FINISHED
            false -> TASK_ITEM_UNFINISHED
        }
    }

    companion object {
        private const val TASK_ITEM_FINISHED = 101
        private const val TASK_ITEM_UNFINISHED = 192
    }
}