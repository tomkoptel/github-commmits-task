package com.olderwold.jlabs.github.feature.data.dto

import com.google.gson.annotations.SerializedName

internal data class Tree(
    @SerializedName("sha")
    val sha: String? = null,
    @SerializedName("url")
    val url: String? = null
)
