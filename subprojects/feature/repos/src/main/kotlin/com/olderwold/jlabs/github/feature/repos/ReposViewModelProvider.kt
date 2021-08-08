package com.olderwold.jlabs.github.feature.repos

import com.olderwold.jlabs.github.feature.repos.ui.ReposViewModel

@Suppress("UnnecessaryAbstractClass")
abstract class ReposViewModelProvider {
    internal abstract fun provide(): ReposViewModel
}
