package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.core.os.ConfigurationCompat
import com.olderwold.jlabs.github.feature.details.domain.RepoCommit
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

internal data class YearReport(
    val year: Int,
    val totalCommits: Int = 0,
    val monthReports: List<MonthReport> = emptyList(),
) {
    class Factory(
        private val locale: Locale,
    ) {
        companion object {
            operator fun invoke(application: Application): Factory {
                val configuration = application.resources.configuration
                val currentLocale = ConfigurationCompat
                    .getLocales(configuration)
                    .get(0) ?: Locale.getDefault()
                return Factory(
                    locale = currentLocale,
                )
            }
        }

        fun create(repoDetails: RepoDetails, year: Int): YearReport = when (repoDetails) {
            is RepoDetails.Empty -> YearReport(year = year)
            is RepoDetails.NonEmpty -> yearReport(repoDetails, year = year)
        }

        private fun yearReport(
            repoDetails: RepoDetails.NonEmpty,
            year: Int
        ): YearReport {
            val totalCommits = repoDetails.commits.size
            val montReports = Month.values().map { month ->
                monthReport(
                    month = month,
                    repoDetails = repoDetails,
                ) { it.dateTime.month == month && it.dateTime.year == year }
            }

            return YearReport(
                year = year,
                totalCommits = totalCommits,
                monthReports = montReports,
            )
        }

        private fun monthReport(
            repoDetails: RepoDetails.NonEmpty,
            month: Month,
            predicate: (RepoCommit) -> Boolean,
        ): MonthReport {
            val totalCommits = repoDetails.commits.size
            val commitsPerMonth = repoDetails.commits.filter(predicate)
            val monthName = month.getDisplayName(TextStyle.FULL_STANDALONE, locale)

            return MonthReport(
                totalCommits = totalCommits,
                commitNumbers = commitsPerMonth.size,
                month = monthName
            )
        }
    }
}
