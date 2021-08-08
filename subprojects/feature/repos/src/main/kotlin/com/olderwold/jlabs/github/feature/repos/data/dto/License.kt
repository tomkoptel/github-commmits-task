package com.olderwold.jlabs.github.feature.repos.data.dto

import com.google.gson.annotations.SerializedName

internal data class License(
    @SerializedName("key")
    val key: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("node_id")
    val nodeId: String? = null,
    @SerializedName("spdx_id")
    val spdxId: String? = null,
    @SerializedName("url")
    val url: Any? = null
)
