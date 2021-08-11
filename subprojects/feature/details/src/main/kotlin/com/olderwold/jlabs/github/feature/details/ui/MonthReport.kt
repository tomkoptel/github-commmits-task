package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails

data class MonthReport(
    val totalCommits: Int,
    val commitNumbers: Int,
    val month: String,
) {
    val maxHeight: Dp = 80.dp
    val maxWidth: Dp = 10.dp

    val barHeight: Dp
        get() {
            return maxHeight * (commitNumbers.toFloat() / totalCommits.toFloat())
        }

    class Factory(
        private val application: Application,
    ) {
        fun create(repoDetails: RepoDetails): List<MonthReport> {
            return emptyList()
        }
    }
}
