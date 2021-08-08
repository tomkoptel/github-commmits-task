package com.olderwold.jlabs.github.feature.repos.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.olderwold.jlabs.github.feature.repos.domain.Repo

@Composable
@Preview(showBackground = true)
internal fun RepoItem(
    @PreviewParameter(provider = FeedItemProvider::class) item: Repo,
    onItemClicked: (repoName: String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClicked(item.name) },
        elevation = 8.dp,

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(item.id, fontSize = 20.sp)
            if (item.name.isNotBlank()) {
                Text(item.name, fontSize = 20.sp)
            }
            if (!item.description.isNullOrEmpty()) {
                Text("${item.description}", fontSize = 15.sp)
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
internal fun Waiting() {
    Text(
        text = "Waiting for items to load from the backend",
        modifier = Modifier
            .fillMaxWidth(fraction = 1f)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
@Preview(showBackground = true)
internal fun Error(message: String? = null) {
    Text(
        text = "Ops! Something went wrong: $message",
        modifier = Modifier
            .fillMaxWidth(fraction = 1f)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

internal class FeedItemProvider : PreviewParameterProvider<Repo> {
    override val values: Sequence<Repo> = sequenceOf(
        Repo(
            id = "id",
            name = "The Lord of the Rings",
            url = "https://my.precious.com",
            description = "Let start our journey!"
        )
    )
}
