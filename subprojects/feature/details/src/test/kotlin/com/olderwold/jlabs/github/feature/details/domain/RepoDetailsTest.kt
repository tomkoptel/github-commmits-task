package com.olderwold.jlabs.github.feature.details.domain

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldContain
import org.junit.Test
import java.time.ZonedDateTime

class RepoDetailsTest {
    private val dummy = RepoDetails(name = "?", commits = emptyList())
    private val commit2013 = RepoCommit(dateTime = ZonedDateTime.parse("2013-10-02T23:58:48Z"))
    private val commit2014 = RepoCommit(dateTime = ZonedDateTime.parse("2014-10-02T23:58:48Z"))
    private val commit2015 = RepoCommit(dateTime = ZonedDateTime.parse("2015-10-02T23:58:48Z"))

    @Test
    fun filterByYear2013() {
        val repoDetails = dummy.copy(commits = listOf(commit2013, commit2015))
        repoDetails.filterByYear(year = 2013) shouldContain commit2013
    }

    @Test
    fun filterByYear1999() {
        val repoDetails = dummy.copy(commits = listOf(commit2013, commit2015))
        repoDetails.filterByYear(year = 1999).shouldBeEmpty()
    }

    @Test
    fun latestShouldBeTheBiggestYear() {
        val repoDetails = dummy.copy(commits = listOf(commit2014, commit2013, commit2015))

        repoDetails.closest() shouldContain commit2015
    }

    @Test
    fun latestHandlesEmptyCase() {
        dummy.closest().shouldBeEmpty()
    }
}
