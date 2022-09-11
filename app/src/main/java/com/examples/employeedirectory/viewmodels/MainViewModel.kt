package com.examples.employeedirectory.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.examples.employeedirectory.Employee

/**
 * View Model for Main Activity screen.
 */
class MainViewModel : ViewModel() {
    private val _employeesListLiveData: MutableLiveData<List<Employee>?> = MutableLiveData(null)
    val employeeListLiveData: LiveData<List<Employee>?> = _employeesListLiveData
    private val sampleList = listOf(
        Employee(
            "abc",
            "Joseph",
            "222",
            "abc@xyz.com",
            team = "Point Of sale",
            photo_url_small = "",
            photo_url_large = ""
        ),
        Employee(
            "abc",
            "Joseph",
            "222",
            "abc@xyz.com",
            team = "Point Of sale",
            photo_url_small = "",
            photo_url_large = ""
        ),
        Employee(
            "abc",
            "Joseph",
            "2422",
            "abc@xyz.com",
            team = "Point Of sale",
            photo_url_small = "",
            photo_url_large = ""
        )
    )

    fun retrieveEmployeeList() {
        // Fetcht the list from the service and update the live data with it.
        _employeesListLiveData.postValue(sampleList)
    }
}