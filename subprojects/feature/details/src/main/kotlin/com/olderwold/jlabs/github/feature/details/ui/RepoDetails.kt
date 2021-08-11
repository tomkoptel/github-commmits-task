package com.olderwold.jlabs.github.feature.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val CommitColor = Color(0xff00efce)

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
}

@Composable
fun RepoDetails(items: List<MonthReport>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(items) {
            MonthCommits(it)
        }
    }
}

@Composable
fun MonthCommits(
    item: MonthReport,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.height(IntrinsicSize.Min),
    ) {
        CommitBar(
            barHeight = item.barHeight,
            modifier = Modifier.requiredSize(
                width = item.maxWidth,
                height = item.maxHeight,
            )
        )
        Text(
            text = item.month,
        )
    }
}

@Composable
fun CommitBar(
    modifier: Modifier = Modifier,
    barHeight: Dp,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .background(Color.Gray),
        )
        Box(
            modifier = Modifier.height(barHeight)
                .fillMaxWidth()
                .background(CommitColor),
        )
    }
}

@Composable
@Preview(name = "Default")
internal fun PreviewRepoDetails() {
    RepoDetails(
        listOf(
            MonthReport(
                totalCommits = 100,
                commitNumbers = 90,
                month = "April",
            ),
            MonthReport(
                totalCommits = 100,
                commitNumbers = 10,
                month = "March",
            ),
            MonthReport(
                totalCommits = 100,
                commitNumbers = 10,
                month = "May",
            ),
            MonthReport(
                totalCommits = 100,
                commitNumbers = 0,
                month = "June",
            ),
        )
    )
}

@Composable
@Preview
fun CommitBarPreview() {
    CommitBar(
        barHeight = 20.dp,
        modifier = Modifier
            .requiredSize(
                width = 10.dp,
                height = 80.dp,
            )
    )
}
