package com.olderwold.jlabs.github.feature.details.data.dto

import com.google.gson.annotations.SerializedName

internal data class Verification(
    @SerializedName("payload")
    val payload: Any? = null,
    @SerializedName("reason")
    val reason: String? = null,
    @SerializedName("signature")
    val signature: Any? = null,
    @SerializedName("verified")
    val verified: Boolean? = null
)
