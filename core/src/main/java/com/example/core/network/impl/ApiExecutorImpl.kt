package com.example.core.network.impl

import com.example.core.network.contract.ApiExecutor
import jakarta.inject.Inject
import retrofit2.Response
import com.example.core.utility.Result


private const val ERROR_MSG_BODY_NULL = "Response body is null"
private const val ERROR_MSG_UNCAUGHT_EXCEPTION = "Uncaught exception"

class ApiExecutorImpl @Inject constructor() : ApiExecutor {
    override suspend fun <T> invoke(executionBlock: suspend () -> Response<T>): Result<T> {
        try {
            val result = executionBlock()
            if (result.isSuccessful) {
                return result.body()?.let {
                   Result.Success(it)
                } ?: kotlin.run {
                   Result.Error(Exception(ERROR_MSG_BODY_NULL))
                }
            }
        } catch (exception: Exception){
            when (exception) {
                is RuntimeException -> {
                    return Result.Error(exception)
                }
            }
        }
        return Result.Error(Exception(ERROR_MSG_UNCAUGHT_EXCEPTION))
    }

}