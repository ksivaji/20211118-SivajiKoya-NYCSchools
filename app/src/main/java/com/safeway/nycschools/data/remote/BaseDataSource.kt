package com.safeway.nycschools.data.remote

import com.safeway.nycschools.data.Result
import com.safeway.nycschools.data.remote.interceptors.ConnectivityInterceptor.NoConnectivityException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T : Any> getResult(call: suspend () -> Response<T>): Result<T> {

        try {
            val response = call()
            val body = response.body()
            val code = response.code()

            return if (response.isSuccessful) {
                if (body != null) {
                    Result.Success(body)
                } else {
                    // Response is success, but the body is null
                    Result.Error.UnknownError()
                }
            } else {
                /**
                 * TODO: based on error code, more specific error can be returned. For now, returning unknown error
                 */

                Result.Error.UnknownError()
            }

        } catch (exception: Exception) {
            return when (exception) {
                is NoConnectivityException -> Result.Error.NoConnectivity
                is SocketTimeoutException, is ConnectException -> Result.Error.ServerIssue
                else -> Result.Error.UnknownError()
            }
        }
    }

}
