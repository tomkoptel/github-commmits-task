package com.olderwold.jlabs.github.feature.data

import com.olderwold.jlabs.github.feature.data.dto.RepoDetailsItem
import com.olderwold.jlabs.github.retrofit.Cacheable
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

internal interface GithubApi {
    companion object {
        operator fun invoke(
            retrofitBuilder: Retrofit.Builder.() -> Unit = {},
            clientBuilder: OkHttpClient.Builder.() -> Unit = {},
        ): GithubApi {
            val okHttpClient = OkHttpClient.Builder()
                .apply(clientBuilder)
                .build()
            return Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .apply(retrofitBuilder)
                .build()
                .create(GithubApi::class.java)
        }
    }

    @Cacheable(until = 5, unit = TimeUnit.MINUTES)
    @GET("/repos/mralexgray/{repo}/commits")
    suspend fun repoDetails(@Path(value = "repo") repo: String): List<RepoDetailsItem>
}
