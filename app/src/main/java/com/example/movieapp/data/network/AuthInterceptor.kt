package com.example.movieapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        val url = req.url().newBuilder().addQueryParameter("api_key", "a31a04513014eec4a7e65dc032177ad7").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}