package com.olderwold.jlabs.github.feature.details.data

import com.olderwold.jlabs.github.feature.details.data.dto.RepoCommitItem
import com.olderwold.jlabs.github.feature.details.domain.RepoCommit
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails
import java.time.ZonedDateTime
import java.time.format.DateTimeParseException

internal class RepoDetailsFactory {
    fun create(name: String, commitsData: List<RepoCommitItem>): RepoDetails {
        val commits = commitsData.mapNotNull(::mapToDomain)
        val commitsByYear = commits.groupBy { it.dateTime }
        val firstCommitDate = commitsByYear.keys.firstOrNull()
        val lastCommitDate = commitsByYear.keys.lastOrNull()

        return if (firstCommitDate != null && lastCommitDate != null) {
            RepoDetails.NonEmpty(
                name = name,
                commits = commits,
                firstCommitDate = firstCommitDate,
                lastCommitDate = lastCommitDate,
            )
        } else {
            RepoDetails.Empty(name)
        }
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
