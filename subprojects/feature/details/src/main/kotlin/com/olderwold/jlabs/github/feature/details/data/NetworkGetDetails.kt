package com.olderwold.jlabs.github.feature.details.data

import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails
import retrofit2.HttpException
import java.net.HttpURLConnection

internal class NetworkGetDetails(
    private val githubApi: GithubApi,
    private val repoDetailsFactory: RepoDetailsFactory,
) : GetDetails {
    constructor(githubApi: GithubApi) : this(githubApi, RepoDetailsFactory())

    override suspend fun invoke(
        repoName: String
    ): RepoDetails {
        return try {
            val commitsData = githubApi.repoDetails(repoName)
            repoDetailsFactory.create(repoName, commitsData)
        } catch (ex: HttpException) {
            // The better approach is to implement custom call adapter factory that will handle
            // generic errors according to the common contract defined API
            // For the example, we might wrap response as GithubResponse<T>
            val isAConflict = ex.code() == HttpURLConnection.HTTP_CONFLICT

            if (isAConflict) {
                RepoDetails.Empty(repoName)
            } else {
                throw ex
            }
        }
    }
}
