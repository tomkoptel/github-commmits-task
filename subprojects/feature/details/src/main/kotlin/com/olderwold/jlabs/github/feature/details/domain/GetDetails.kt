package com.olderwold.jlabs.github.feature.details.domain

internal interface GetDetails {
    suspend operator fun invoke(
        repoName: String
    ): RepoDetails
}
