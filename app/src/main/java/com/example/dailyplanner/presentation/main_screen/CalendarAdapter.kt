package com.example.dailyplanner.presentation.main_screen

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import com.example.dailyplanner.R
import java.time.LocalDate

class CalendarAdapter(private val viewModel: MainViewModel) :
    ListAdapter<LocalDate?, CalendarViewHolder>(DiffUtiIItemCallbackCalendar()) {

    var onNumberItemClickListener: ((position: Int, date: LocalDate) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(
            parent.context
        ).inflate(R.layout.calendar_cell, parent, false)
        val layoutParams = view.layoutParams
        layoutParams.height = (parent.height * 0.1666666666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = currentList[position]
        if (date == null) {
            holder.number.text = ""
        } else {
            holder.number.text = date.dayOfMonth.toString()
            holder.itemView.setOnClickListener {
                onNumberItemClickListener?.invoke(position, date)
            }
            if ((date == viewModel.selectedDate.value)) {
                holder.parentView.setBackgroundColor(Color.GRAY)
            }
        }
    }

    override fun getItemCount(): Int {
        return currentList.size
    }
}