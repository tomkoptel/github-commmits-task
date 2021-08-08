package com.olderwold.jlabs.github

import android.widget.Toast
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import com.olderwold.jlabs.github.components.InsetAwareTopAppBar
import com.olderwold.jlabs.github.feature.repos.reposViewModelProvider
import com.olderwold.jlabs.github.feature.repos.ui.RepoPage

@Composable
fun HomeScreen(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) {
    val activity = requireNotNull(LocalContext.current as? FragmentActivity) {
        "Failed to acquire Activity context."
    }
    val viewModelProvider = activity.reposViewModelProvider()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            val title = stringResource(id = R.string.app_name)
            InsetAwareTopAppBar(title = { Text(text = title) })
        }
    ) {
        RepoPage(viewModelProvider = viewModelProvider, onItemClicked = { repoName ->
            Toast.makeText(activity, repoName, Toast.LENGTH_SHORT).show()
        })
    }
}
