package com.olderwold.jlabs.github.feature.repos.data

import com.olderwold.jlabs.github.feature.repos.domain.GetRepos
import com.olderwold.jlabs.github.feature.repos.domain.Repo

internal class NetworkGetRepos(
    private val githubApi: GithubApi,
) : GetRepos {
    override suspend fun invoke(): List<Repo> {
        return githubApi.repos().mapNotNull { dto ->
            val id = dto.id
            val name = dto.name
            val url = dto.url
            val description = dto.description
            if (id != null && name != null && url != null) {
                Repo(
                    id = id.toString(),
                    name = name,
                    url = url,
                    description = description
                )
            } else {
                null
            }
        }
    }
}
