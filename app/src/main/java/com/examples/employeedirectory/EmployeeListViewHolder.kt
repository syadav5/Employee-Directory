package com.examples.employeedirectory

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EmployeeListViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

    val name:TextView = itemView.findViewById(R.id.empName)
    val phone:TextView = itemView.findViewById(R.id.empPhone)
    val email:TextView = itemView.findViewById(R.id.empEmail)
    val photo:ImageView = itemView.findViewById(R.id.empImg)
    val team:TextView = itemView.findViewById(R.id.empTeam)

    companion object {
        val layoutId = R.layout.each_employee_row
    }
}