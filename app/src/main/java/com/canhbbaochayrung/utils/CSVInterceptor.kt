package com.canhbbaochayrung.utils

import com.google.gson.JsonObject
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Invocation

class CSVInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val csvTag = request.tag(Invocation::class.java)?.method()?.getAnnotation(CSVRequest::class.java)
        val shouldOverride = csvTag?.let {
            true
        } ?: kotlin.run { false }
        if (!shouldOverride){
            return chain.proceed(request)
        }
        val response = chain.proceed(request)
        val body = response.peekBody(1024)
        val jsonObject = JsonObject()
        jsonObject.addProperty("data", body.string())
        val value = jsonObject.toString()
        response.close()
        return response.newBuilder().body(value.toResponseBody()).build()
    }
}