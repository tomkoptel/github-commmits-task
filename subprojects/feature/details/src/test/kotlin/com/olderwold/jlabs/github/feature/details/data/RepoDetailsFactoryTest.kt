package com.olderwold.jlabs.github.feature.details.data

import com.olderwold.jlabs.github.feature.details.data.dto.Commit
import com.olderwold.jlabs.github.feature.details.data.dto.Committer
import com.olderwold.jlabs.github.feature.details.data.dto.RepoCommitItem
import com.olderwold.jlabs.github.feature.details.domain.RepoCommit
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeInstanceOf
import org.amshove.kluent.shouldContain
import org.junit.Test
import java.time.ZonedDateTime

class RepoDetailsFactoryTest {
    private val factory = RepoDetailsFactory()
    private val commit2013 = commitDTO(date = "2013-10-02T23:58:48Z")
    private val commit2014 = commitDTO(date = "2014-10-02T23:58:48Z")
    private val commit2015 = commitDTO(date = "2015-10-02T23:58:48Z")

    @Test
    fun `for invalid commit date should filter result`() {
        val repoDetails = factory.create(name = "?", commitsData = listOf(
            commitDTO(date = "I am incorrect date time with zone")
        ))
        repoDetails.shouldBeInstanceOf(RepoDetails.Empty::class)
    }

    @Test
    fun `for empty commits should map name`() {
        val repoDetails = factory.create(name = "?", commitsData = emptyList())
        repoDetails.shouldBeInstanceOf(RepoDetails.Empty::class)
        repoDetails.name shouldBeEqualTo "?"
    }

    @Test
    fun `for non empty commits should map name`() {
        val repoDetails = factory.create(name = "?", commitsData = listOf(commit2013))
        repoDetails.name shouldBeEqualTo "?"
    }

    @Test
    fun `for non empty commits should map firstDate`() {
        val repoDetails = mapNonEmpty(listOf(commit2013, commit2014, commit2015))
        repoDetails.firstCommitDate.year shouldBeEqualTo 2013
    }

    @Test
    fun `for non empty commits should map lastCommitDate`() {
        val repoDetails = mapNonEmpty(listOf(commit2013, commit2014, commit2015))
        repoDetails.lastCommitDate.year shouldBeEqualTo 2015
    }

    @Test
    fun `for non empty should produce commit domain entity`() {
        val repoDetails = mapNonEmpty(listOf(commit2013))
        repoDetails.commits shouldContain RepoCommit(
            dateTime = ZonedDateTime.parse("2013-10-02T23:58:48Z")
        )
    }

    private fun mapNonEmpty(
        commitsData: List<RepoCommitItem>
    ): RepoDetails.NonEmpty = factory.create(
        name = "?",
        commitsData = commitsData,
    ) as RepoDetails.NonEmpty

    private fun commitDTO(date: String) : RepoCommitItem = RepoCommitItem(
        commit = Commit(committer = Committer(date = date))
    )
}
