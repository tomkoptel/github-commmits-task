package com.olderwold.jlabs.github.feature.details.data.dto

import com.google.gson.annotations.SerializedName

internal data class Committer(
    @SerializedName("date")
    val date: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("name")
    val name: String? = null
)
