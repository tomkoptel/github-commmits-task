package com.olderwold.jlabs.github.retrofit

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

internal class OfflineCacheInterceptor(
    private val isNetworkAvailable: () -> Boolean,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val invocation: Invocation? = request.tag(Invocation::class.java)

        if (invocation != null) {
            val annotation: Cacheable? = invocation.method().getAnnotation(Cacheable::class.java)
            if (annotation != null) {
                if (!isNetworkAvailable()) {
                    val cacheControl = CacheControl.Builder()
                        .maxStale(annotation.until, annotation.unit)
                        .build()

                    return request
                        .newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .cacheControl(cacheControl)
                        .build()
                        .let(chain::proceed)
                }
            }
        }
        return chain.proceed(chain.request())
    }
}
