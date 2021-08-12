package com.olderwold.jlabs.github.feature.details.data

import com.olderwold.jlabs.github.feature.details.data.dto.RepoCommitItem
import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import com.olderwold.jlabs.github.feature.details.domain.RepoCommit
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

internal class NetworkGetDetails(
    private val githubApi: GithubApi,
) : GetDetails {
    override suspend fun invoke(
        repoName: String
    ): RepoDetails {
        val commitsData = githubApi.repoDetails(repoName)
        val commits = commitsData.mapNotNull(::mapToDomain)
        return RepoDetails(name = repoName, commits = commits)
    }

    private fun mapToDomain(commit: RepoCommitItem): RepoCommit? {
        val dateTime = try {
            commit.commit?.committer?.date?.let {
                ZonedDateTime.parse(it)
            }
        } catch (ex: DateTimeParseException) {
            null
        }
        return dateTime?.let { RepoCommit(dateTime) }
    }
}
