package com.olderwold.jlabs.github.feature.details.data.dto

import com.google.gson.annotations.SerializedName

internal data class Parent(
    @SerializedName("html_url")
    val htmlUrl: String? = null,
    @SerializedName("sha")
    val sha: String? = null,
    @SerializedName("url")
    val url: String? = null
)
