package com.example.dailyplanner.presentation.main_screen

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyplanner.R

class CalendarViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val number = view.findViewById<TextView>(R.id.cell_day_number)
    val parentView = view.findViewById<ConstraintLayout>(R.id.parent_view)
}