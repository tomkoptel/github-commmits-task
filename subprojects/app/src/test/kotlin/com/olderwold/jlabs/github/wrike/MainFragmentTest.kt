package com.olderwold.jlabs.github.wrike

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import com.olderwold.jlabs.github.wrike.MainViewModel.Data
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    private val liveData = MutableLiveData(emptyList<Data>())

    private val viewModelMock = mockk<MainViewModel>() {
        every { dataLive } returns liveData
    }

    @Test
    fun `when fragment resumed should show list of items`() {
        liveData.value = listOf(
            Data(title = "Bilbo Baggins")
        )
        val scenario = launchFragmentInContainer<MainFragment>(
            factory = fragmentFactory {
                overrideViewModel { viewModelMock }
            }
        )

        scenario.moveToState(Lifecycle.State.RESUMED)

        assertDisplayed(android.R.id.edit)
        assertDisplayed(android.R.id.list)

        scrollListToPosition(android.R.id.list, 0)
        assertDisplayed(android.R.id.text1, "Bilbo Baggins")
    }
}
