package com.olderwold.jlabs.github.wrike

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.adevinta.android.barista.interaction.BaristaListInteractions.scrollListToPosition
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {
    @Test
    fun `when fragment resumed should show list of items`() {
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.moveToState(Lifecycle.State.RESUMED)

        assertDisplayed(android.R.id.edit)
        assertDisplayed(android.R.id.list)

        scrollListToPosition(android.R.id.list, 0)
        assertDisplayed(android.R.id.text1, "Mesocricetus auratus")
    }
}
