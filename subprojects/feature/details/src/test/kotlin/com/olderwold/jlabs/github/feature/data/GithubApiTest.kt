package com.olderwold.jlabs.github.feature.data

import com.olderwold.jlabs.github.tape.tape
import kotlinx.coroutines.runBlocking
import okreplay.OkReplay
import org.amshove.kluent.shouldNotBeEmpty
import org.junit.Rule
import org.junit.Test

class GithubApiTest {
    @get:Rule
    internal val testRule = tape {
        GithubApi { addInterceptor(it) }
    }

    @Test
    @OkReplay
    fun smokeTest(): Unit = runBlocking {
        val hotListing = testRule.api.repoDetails(repo = "ACEView")
        hotListing.shouldNotBeEmpty()
    }
}
