package com.examples.employeedirectory.viewmodels

import androidx.lifecycle.*
import com.examples.employeedirectory.data.Employee
import com.examples.employeedirectory.service.EmployeeService
import com.examples.employeedirectory.service.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

/**
 * View Model for Main Activity screen.
 */
class MainViewModel(private val employeeService: EmployeeService) : ViewModel() {
    private val _employeesListLiveData: MutableLiveData<Resource<List<Employee>?>?> = MutableLiveData(null)
    val employeeListLiveData: LiveData<Resource<List<Employee>?>?> = _employeesListLiveData

    /**
     * This method makes service call and retrieves the employees list.
     */
    fun retrieveEmployeeList(forceRefresh:Boolean = false) {
        // Fetch the list from the service and update the live data with it.
        if (_employeesListLiveData.value == null || forceRefresh) {
            _employeesListLiveData.postValue(Resource.Loading())
            viewModelScope.launch {
                val response = employeeService.getEmployees()
                _employeesListLiveData.postValue(response)
            }
        }
    }
}

class MainViewModelFactory constructor(private val service: EmployeeService):
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            MainViewModel(employeeService = service) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}
