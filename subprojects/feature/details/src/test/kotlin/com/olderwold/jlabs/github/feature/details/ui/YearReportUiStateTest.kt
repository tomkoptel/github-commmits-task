package com.olderwold.jlabs.github.feature.details.ui

import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeFalse
import org.amshove.kluent.shouldBeTrue
import org.junit.Test

class YearReportUiStateTest {
    private val year2020 = Result.success(YearReport(year = 2020))
    private val year2021 = Result.success(YearReport(year = 2021))

    @Test
    fun `current=loading and next=loading contents the same`() {
        YearReportUiState.Loading
            .areContentsTheSame(YearReportUiState.Loading)
            .shouldBeTrue()
    }

    @Test
    fun `current=loading and next=loaded contents are not the same`() {
        YearReportUiState.Loading.areContentsTheSame(
            YearReportUiState.Loaded(
                previousResult = null,
                currentResult = year2020
            )
        ).shouldBeFalse()
    }

    @Test
    fun `current=loaded and next=loading contents are not the same`() {
        YearReportUiState.Loaded(
            previousResult = null,
            currentResult = year2020
        ).areContentsTheSame(YearReportUiState.Loading).shouldBeFalse()
    }

    @Test
    fun `current=loaded and next=loaded should compare currentResult`() {
        YearReportUiState.Loaded(
            previousResult = null,
            currentResult = year2020
        ).areContentsTheSame(
            YearReportUiState.Loaded(
                previousResult = null,
                currentResult = year2020
            )
        ).shouldBeTrue()

        YearReportUiState.Loaded(
            previousResult = year2020,
            currentResult = year2021
        ).areContentsTheSame(
            YearReportUiState.Loaded(
                previousResult = null,
                currentResult = year2021
            )
        ).shouldBeTrue()

        YearReportUiState.Loaded(
            previousResult = null,
            currentResult = year2020
        ).areContentsTheSame(
            YearReportUiState.Loaded(
                previousResult = null,
                currentResult = year2021
            )
        ).shouldBeFalse()
    }

    @Test
    fun `reduce combines states for loaded`() {
        YearReportUiState.Loaded(
            previousResult = null,
            currentResult = year2020
        ).reduce(
            YearReportUiState.Loaded(
                previousResult = null,
                currentResult = year2021
            )
        ) shouldBeEqualTo YearReportUiState.Loaded(
            previousResult = year2020,
            currentResult = year2021
        )
    }

    @Test
    fun `reduce combines states for loading`() {
        val newState = YearReportUiState.Loading.reduce(
            YearReportUiState.Loaded(
                previousResult = null,
                currentResult = year2021
            )
        )
        newState shouldBeEqualTo YearReportUiState.Loaded(
            previousResult = null,
            currentResult = year2021
        )
    }

    @Test
    fun `reduce combines states for loaded with loading`() {
        YearReportUiState.Loaded(
            previousResult = year2020,
            currentResult = year2021
        ).reduce(YearReportUiState.Loading) shouldBeEqualTo YearReportUiState.Loading
    }
}
