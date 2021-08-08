package com.olderwold.jlabs.github.feature.repos.domain

internal interface GetRepos {
    suspend operator fun invoke(): List<Repo>
}
