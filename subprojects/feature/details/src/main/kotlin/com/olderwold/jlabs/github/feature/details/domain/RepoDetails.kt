package com.olderwold.jlabs.github.feature.details.domain

import java.time.ZonedDateTime

internal sealed class RepoDetails(
    open val name: String,
) {
    abstract val firstCommitDate: ZonedDateTime?

    data class Empty(override val name: String) : RepoDetails(name) {
        override val firstCommitDate: ZonedDateTime? = null
    }

    data class NonEmpty(
        override val name: String,
        val commits: List<RepoCommit>,
        override val firstCommitDate: ZonedDateTime,
        val lastCommitDate: ZonedDateTime,
    ) : RepoDetails(name)
}
