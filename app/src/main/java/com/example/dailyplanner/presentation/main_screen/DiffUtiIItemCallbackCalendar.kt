package com.example.dailyplanner.presentation.main_screen

import androidx.recyclerview.widget.DiffUtil
import java.time.LocalDate

class DiffUtiIItemCallbackCalendar : DiffUtil.ItemCallback<LocalDate?>() {

    override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
        return oldItem.month == newItem.month
    }

    override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
        return oldItem == newItem
    }

}