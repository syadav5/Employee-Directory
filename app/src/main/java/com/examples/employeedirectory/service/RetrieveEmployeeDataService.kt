package com.examples.employeedirectory.service

import com.examples.employeedirectory.data.GetEmployeesResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Retrofit Service to retrieve employees data.
 */
interface RetrieveEmployeeRetrofitService {

    @GET(RestEndPoints.EMPLOYEES_ENDPOINT)
    suspend fun getAllEmployees(): Response<GetEmployeesResponse>

    companion object {
        lateinit var retrieveEmployeeService: RetrieveEmployeeRetrofitService
        private const val BASE_URL = RestEndPoints.BASE_URL

        fun getInstance(): RetrieveEmployeeRetrofitService {
            if (!this::retrieveEmployeeService.isInitialized) {
                val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
                retrieveEmployeeService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create(moshi))
                    .build()
                    .create(RetrieveEmployeeRetrofitService::class.java)
            }
            return retrieveEmployeeService
        }
    }
}