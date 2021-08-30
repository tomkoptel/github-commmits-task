package com.olderwold.jlabs.github.wrike

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.olderwold.jlabs.github.wrike.MainViewModel.Data
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `should load the data on instantiation`() {
        val viewModel = MainViewModel(currentThreadExecutorService())

        viewModel.dataLive.value shouldBeEqualTo listOf(
            Data(title = "Mesocricetus auratus"),
            Data(title = "Phodopus sungorus"),
            Data(title = "Phodopus campbelli")
        )
    }

    @Test
    fun `should filter by Phodopus`() {
        val viewModel = MainViewModel(currentThreadExecutorService())

        viewModel.filter(query = "Phodopus")
        viewModel.dataLive.value shouldBeEqualTo listOf(
            Data(title = "Phodopus sungorus"),
            Data(title = "Phodopus campbelli")
        )
    }

    @Test
    fun `should reset to default if query empty`() {
        val viewModel = MainViewModel(currentThreadExecutorService())

        viewModel.filter(query = "Phodopus")
        viewModel.filter(query = "")
        viewModel.dataLive.value shouldBeEqualTo listOf(
            Data(title = "Mesocricetus auratus"),
            Data(title = "Phodopus sungorus"),
            Data(title = "Phodopus campbelli")
        )
    }
}
