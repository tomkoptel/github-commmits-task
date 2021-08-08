package com.olderwold.jlabs.github.feature.repos

import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.olderwold.jlabs.github.feature.repos.ui.ReposViewModel

fun FragmentActivity.reposViewModelProvider(): ReposViewModelProvider {
    val viewModel by viewModels<ReposViewModel> {
        ReposViewModel.Factory
    }
    return object : ReposViewModelProvider() {
        override fun provide(): ReposViewModel {
            return viewModel
        }
    }
}
