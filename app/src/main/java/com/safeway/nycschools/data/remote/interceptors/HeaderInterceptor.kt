package com.safeway.nycschools.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeaderInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("Accept", "application/json")
            .addHeader("X-App-Token", "ZvKKjYfpwQmmYPx5p80gFvWSU") //TODO: store token at common place and load from there
            .build()
        return chain.proceed(request)
    }

}