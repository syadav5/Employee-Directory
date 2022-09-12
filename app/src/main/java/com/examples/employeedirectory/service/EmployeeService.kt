package com.examples.employeedirectory.service

import android.util.Log
import com.examples.employeedirectory.data.Employee

object EmployeeService {
    private const val TAG = "EmployeeService"

    private val retrofitService: RetrieveEmployeeRetrofitService =
        RetrieveEmployeeRetrofitService.getInstance()

    suspend fun getEmployees(): Resource<List<Employee>?> {
        Log.d(TAG, "Going to make a service call")
        val response = retrofitService.getAllEmployees()
        if (response.isSuccessful) {
            return Resource.Success(response.body()?.employees ?: listOf())
        } else {
            return Resource.Error(response.code().toString(), response.message())
        }
    }
}
