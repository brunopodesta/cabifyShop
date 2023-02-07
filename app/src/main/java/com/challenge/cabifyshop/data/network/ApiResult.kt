package com.challenge.cabifyshop.data.network

sealed class ApiResult() {

    class Success<T>(val data : T) : ApiResult()
    class Error(val errorMessage : String) : ApiResult()
}
