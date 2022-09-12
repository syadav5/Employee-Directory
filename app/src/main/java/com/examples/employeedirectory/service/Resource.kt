package com.examples.employeedirectory.service

sealed class Resource<T>(
    val status: Status,
    val data: T? = null,
    val errorCode: String? = null,
    val errorMsg: String? = null
) {
    class Success<T>(data: T) : Resource<T>(Status.SUCCESS, data = data, null, null)
    class Error<T>(errorCode: String?, errorMsg: String?) :
        Resource<T>(Status.ERROR, null, errorCode, errorMsg)
    class Loading<T> : Resource<T>(Status.LOADING)
    enum class Status { SUCCESS, ERROR, LOADING }
}
