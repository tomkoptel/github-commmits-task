package com.olderwold.jlabs.github

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.olderwold.jlabs.github.components.InsetAwareTopAppBar
import com.olderwold.jlabs.github.feature.details.ui.RepoDetailsPage

@Composable
fun CommitsScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    repoName: String,
    onBack: () -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            InsetAwareTopAppBar(
                title = { Text(text = repoName) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_up)
                        )
                    }
                }
            )
        }
    ) {
        RepoDetailsPage(repoName)
    }
}
