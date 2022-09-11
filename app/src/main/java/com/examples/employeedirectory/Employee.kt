package com.examples.employeedirectory

data class Employee(
    val uuid:String,
    val fullName:String,
    val phoneNumber: String,
    val email:String,
    val team:String,
    val photo_url_small:String,
    val photo_url_large:String,
)