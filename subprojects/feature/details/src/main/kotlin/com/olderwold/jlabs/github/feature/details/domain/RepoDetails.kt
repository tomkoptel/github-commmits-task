package com.olderwold.jlabs.github.feature.details.domain

import java.time.ZonedDateTime

internal sealed class RepoDetails(
    open val name: String,
) {
    data class Empty(override val name: String) : RepoDetails(name)

    data class NonEmpty(
        override val name: String,
        val commits: List<RepoCommit>,
        val firstCommitDate: ZonedDateTime,
        val lastCommitDate: ZonedDateTime,
    ) : RepoDetails(name)
}
