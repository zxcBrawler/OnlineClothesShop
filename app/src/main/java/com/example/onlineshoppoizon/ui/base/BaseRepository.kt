package com.example.onlineshoppoizon.ui.base

import android.util.Log
import com.example.onlineshoppoizon.retrofit.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : Resource<T>{
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            }catch (throwable : Throwable){
                when(throwable){
                    is HttpException -> {
                        Log.e("http exception", throwable.toString())
                        Resource.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody())
                    }
                    else -> {
                        Log.e("something went wrong", throwable.toString())
                        Resource.Failure(
                            true,
                            throwable.hashCode(),
                            null)
                    }
                }
            }
        }
    }
}