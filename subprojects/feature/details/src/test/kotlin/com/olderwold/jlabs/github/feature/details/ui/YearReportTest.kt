package com.olderwold.jlabs.github.feature.details.ui

import com.olderwold.jlabs.github.feature.details.domain.RepoCommit
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails
import org.amshove.kluent.shouldBe
import org.amshove.kluent.shouldContain
import org.junit.Test
import java.time.LocalDate
import java.time.ZonedDateTime
import java.util.Locale

class YearReportTest {
    private val commit2013_1 = RepoCommit(dateTime = ZonedDateTime.parse("2013-09-02T23:58:48Z"))
    private val commit2013_2 = RepoCommit(dateTime = ZonedDateTime.parse("2013-10-02T23:58:48Z"))
    private val commit2013_3 = RepoCommit(dateTime = ZonedDateTime.parse("2013-10-03T23:58:48Z"))
    private val commit2015 = RepoCommit(dateTime = ZonedDateTime.parse("2015-10-02T23:58:48Z"))

    private val factory = YearReport.Factory(
        locale = Locale.US
    )

    @Test
    fun year2013_with_3_commits() {
        val repoDetails = RepoDetails.NonEmpty(
            name = "?",
            firstCommitDate = commit2013_1.dateTime,
            lastCommitDate = commit2015.dateTime,
            commits = listOf(
                commit2013_1,
                commit2013_2,
                commit2013_3,
            )
        )
        val yearReport = factory.create(repoDetails, year = 2013)
        yearReport.totalCommits shouldBe 4
        yearReport.monthReports shouldContain MonthReport(
            totalCommits = 4,
            commitNumbers = 0,
            month = "August"
        )
        yearReport.monthReports shouldContain MonthReport(
            totalCommits = 4,
            commitNumbers = 1,
            month = "September"
        )
        yearReport.monthReports shouldContain MonthReport(
            totalCommits = 4,
            commitNumbers = 2,
            month = "October"
        )
    }
}
