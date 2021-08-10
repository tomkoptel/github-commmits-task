package com.olderwold.jlabs.github.wip

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val CommitColor = Color(0xff00efce)

data class MonthReport(
    val totalCommits: Int,
    val commitNumbers: Int,
    val month: String,
) {
    val barHeight: Dp
        get() {
            return 50.dp * (commitNumbers.toFloat() / totalCommits.toFloat())
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
fun RepoDetails(items: List<MonthReport>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = Modifier.requiredHeightIn(
            max = 80.dp,
        )
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
        modifier = Modifier.fillMaxHeight(),
    ) {
        CommitBar(
            modifier = Modifier.requiredSize(
                width = 10.dp,
                height = item.barHeight,
            )
        )
        Text(
            text = item.month,
        )
    }
}


@Composable
@Preview(
    showBackground = true,
    widthDp = 10,
    heightDp = 30,
)
fun CommitBar(
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        drawRect(
            color = CommitColor,
            size = size
        )
    }
}

internal class MonthReportProvider : PreviewParameterProvider<MonthReport> {
    override val values: Sequence<MonthReport> = sequenceOf(
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
}
