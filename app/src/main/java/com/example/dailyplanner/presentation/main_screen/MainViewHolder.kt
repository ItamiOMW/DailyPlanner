package com.example.dailyplanner.presentation.main_screen

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyplanner.R

class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val tvPlannedTimeFrom = view.findViewById<TextView>(R.id.tv_planned_time_from)
    val tvPlannedTimeTo = view.findViewById<TextView>(R.id.tv_planned_time_to)
    val tvName = view.findViewById<TextView>(R.id.tv_task)
}