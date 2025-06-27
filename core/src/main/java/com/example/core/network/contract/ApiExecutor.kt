package com.example.core.network.contract

import com.example.core.utility.Result
import retrofit2.Response

interface ApiExecutor {
    suspend operator fun <T> invoke(executionBlock: suspend ()->Response<T>) : Result<T>
}