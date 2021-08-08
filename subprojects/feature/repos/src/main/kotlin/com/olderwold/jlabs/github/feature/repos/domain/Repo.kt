package com.olderwold.jlabs.github.feature.repos.domain

internal data class Repo(
    val id: String,
    val name: String,
    val url: String,
    val description: String? = null,
)
