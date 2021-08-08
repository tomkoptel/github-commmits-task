package com.olderwold.jlabs.github.feature.repos.data

import com.olderwold.jlabs.github.feature.repos.data.dto.ReposDtoItem
import com.olderwold.jlabs.github.retrofit.Cacheable
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
    @GET("/users/mralexgray/repos")
    suspend fun hotListing(
        @Query("limit") limit: Int,
        @Query("after") after: String? = null,
    ): List<ReposDtoItem>
}
