package com.olderwold.jlabs.github.feature.details.domain

internal data class RepoDetails(
    val name: String,
    val commits: List<RepoCommit>
) {
    fun filterByYear(year: Int): List<RepoCommit> {
        return commits.filter { it.dateTime.year == year }
    }

    fun closest(): List<RepoCommit> {
        val commitsByYear = commits.sortedBy { it.dateTime }
            .groupBy { it.dateTime.year }
        return commitsByYear.keys.lastOrNull()?.let { key ->
            commitsByYear[key]
        } ?: emptyList()
    }
}
