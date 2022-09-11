package com.examples.employeedirectory.controllers

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examples.employeedirectory.EmployeeListAdapter
import com.examples.employeedirectory.databinding.ActivityMainBinding
import com.examples.employeedirectory.viewmodels.MainViewModel

/**
 * Landing Activity to show the employees list.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        MainViewModel()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setupRecyclerView()
        setupObservers()
        viewModel.retrieveEmployeeList()
    }

    private fun setupObservers() {
        viewModel.employeeListLiveData.observe(this) {
            // Update the adapter with the latest data and refresh the recycler view.
        }
    }

    private fun setupRecyclerView() {
        _binding.empList.apply {
            adapter = EmployeeListAdapter(viewModel.employeeListLiveData.value?: listOf())
            layoutManager =
                LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }
    }
}