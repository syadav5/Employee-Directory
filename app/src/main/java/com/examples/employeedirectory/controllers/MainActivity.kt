package com.examples.employeedirectory.controllers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.examples.employeedirectory.EmployeeListAdapter
import com.examples.employeedirectory.R
import com.examples.employeedirectory.data.Employee
import com.examples.employeedirectory.databinding.ActivityMainBinding
import com.examples.employeedirectory.service.EmployeeService
import com.examples.employeedirectory.service.Resource
import com.examples.employeedirectory.viewmodels.MainViewModel
import com.examples.employeedirectory.viewmodels.MainViewModelFactory

/**
 * Landing Activity to show the employees list.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var _binding: ActivityMainBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this, MainViewModelFactory(EmployeeService))
            .get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        setupRecyclerView()
        setupObservers()
    }

    override fun onResume() {
        super.onResume()
        viewModel.retrieveEmployeeList()
    }

    private fun setupObservers() {
        viewModel.employeeListLiveData.observe(this) {
            // Update the adapter with the latest data and refresh the recycler view.
            when (it?.status) {
                Resource.Status.SUCCESS -> {
                    _binding.progressInd.visibility = View.GONE
                    it.data?.let {
                        if (it.isNotEmpty()) {
                            refreshList(
                                viewModel.getSortedList(
                                    it,
                                    MainViewModel.SORT_ORDER_NAME_ASC
                                )
                            )
                        } else {
                            showEmptyView()
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    _binding.progressInd.visibility = View.GONE
                    showError()
                }

                Resource.Status.LOADING -> {
                    _binding.progressInd.visibility = View.VISIBLE
                }

                else -> {
                    // do nothing
                }
            }
        }
    }

    private fun showError() {
        _binding.empList.visibility = View.GONE
        _binding.emptyView.apply {
            visibility = View.VISIBLE
            _binding.msgView.text = getString(R.string.error_message)
            _binding.reloadButton.setOnClickListener {
                viewModel.retrieveEmployeeList()
            }
        }
    }

    private fun showEmptyView() {
        _binding.empList.visibility = View.GONE
        _binding.emptyView.apply {
            visibility = View.VISIBLE
            _binding.msgView.text = getString(R.string.no_data_found_error)
        }
        _binding.reloadButton.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        _binding.empList.apply {
            adapter = EmployeeListAdapter(listOf())
            layoutManager =
                LinearLayoutManager(
                    this@MainActivity,
                    RecyclerView.VERTICAL,
                    false
                )
            setHasFixedSize(true)
        }
        _binding.pullRefreshLayout.setOnRefreshListener {
            viewModel.retrieveEmployeeList(true)
            _binding.pullRefreshLayout.isRefreshing = false
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshList(list: List<Employee>) {
        val adapter = EmployeeListAdapter(list)
        _binding.empList.apply {
            this.adapter = adapter
            this.adapter?.notifyDataSetChanged()
            this.visibility = View.VISIBLE
        }
        _binding.emptyView.visibility = View.GONE
    }
}