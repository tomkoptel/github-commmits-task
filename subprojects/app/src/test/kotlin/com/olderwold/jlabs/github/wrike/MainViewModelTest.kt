package com.olderwold.jlabs.github.wrike

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.olderwold.jlabs.github.wrike.MainViewModel.Data
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val lotrFolks = listOf(
        Data(title = "Witch-king of Angmar"),
        Data(title = "Frodo Baggins"),
        Data(title = "Sam Gamgee"),
    )
    private val fakeDataStore = GetData {
        listOf("Witch-king of Angmar", "Frodo Baggins", "Sam Gamgee")
    }

    @Test
    fun `should load the data on instantiation`() {
        createViewModel().dataLive.value shouldBeEqualTo lotrFolks
    }

    @Test
    fun `should filter by Phodopus`() {
        val viewModel = createViewModel()

        viewModel.filter(query = "SAM")
        viewModel.dataLive.value shouldBeEqualTo listOf(
            Data(title = "Sam Gamgee")
        )

        viewModel.filter(query = "sam")
        viewModel.dataLive.value shouldBeEqualTo listOf(
            Data(title = "Sam Gamgee")
        )

        viewModel.filter(query = "sAm")
        viewModel.dataLive.value shouldBeEqualTo listOf(
            Data(title = "Sam Gamgee")
        )
    }

    @Test
    fun `should reset to default if query empty`() {
        val viewModel = createViewModel()

        viewModel.filter(query = "Witch-king")
        viewModel.filter(query = "")
        viewModel.dataLive.value shouldBeEqualTo lotrFolks
    }

    private fun createViewModel() = MainViewModel(
        executorService = currentThreadExecutorService(),
        getData = fakeDataStore
    )
}
