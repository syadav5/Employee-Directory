package com.examples.employeedirectory.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetEmployeesResponse(
    @Json(name ="employees")
    val employees:List<Employee>
)
@JsonClass(generateAdapter = true)
data class Employee(
    @Json(name ="uuid")
    val uuid: String,
    @Json(name="full_name")
    val fullName: String,
    @Json(name="phone_number")
    val phoneNum: String,
    @Json(name="email_address")
    val email: String,
    @Json(name="photo_url_small")
    val photoUrlSmall: String,
    @Json(name="photo_url_large")
    val photoUrlLarge: String,
    @Json(name="team")
    val team: String
):Comparable<Employee> {
    override fun compareTo(other: Employee): Int {
       return this.fullName.compareTo(other.fullName)
    }

}