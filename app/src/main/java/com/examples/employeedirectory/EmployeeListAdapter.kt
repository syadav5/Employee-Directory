package com.examples.employeedirectory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.examples.employeedirectory.data.Employee

class EmployeeListAdapter(private val listOfEmployees: List<Employee>) :
    RecyclerView.Adapter<EmployeeListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(EmployeeListViewHolder.layoutId, parent, false)
        return EmployeeListViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeListViewHolder, position: Int) {
        holder.apply {
            val item = listOfEmployees[position]
            this.name.text = item.fullName
            this.email.text = item.email
            this.phone.text = item.phoneNum
            this.team.text = item.team
            this.photo.load(item.photoUrlSmall) {
                placeholder(R.drawable.ic_baseline_account_box)
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun getItemCount(): Int = listOfEmployees.size
}