package com.olderwold.jlabs.github.retrofit

import okhttp3.OkHttpClient

fun OkHttpClient.Builder.fromCacheIfOffline(
    isNetworkAvailable: () -> Boolean
) {
    addNetworkInterceptor(NetworkInterceptor())
    addInterceptor(OfflineCacheInterceptor(isNetworkAvailable))
}
