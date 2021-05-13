package com.example.movieapp.data.network

import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response

abstract class SafeApiRequest {
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = try {
            call.invoke()
        } catch (e: java.lang.Exception){
            e.printStackTrace()
            throw NoInternetExсeption("No internet connection")
        }
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val error = response.errorBody()?.string()
            val message = StringBuilder()
            error?.let {
                try {
                    message.append(JSONObject(it).getString("status_message"))
                } catch (e: JSONException) {
                }
            }
            throw ApiException(message.toString())
        }
    }
}