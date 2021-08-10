package com.olderwold.jlabs.github.feature.details.data.dto

import com.google.gson.annotations.SerializedName

internal data class Commit(
    @SerializedName("author")
    val author: AuthorX? = null,
    @SerializedName("comment_count")
    val commentCount: Int? = null,
    @SerializedName("committer")
    val committer: Committer? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("tree")
    val tree: Tree? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("verification")
    val verification: Verification? = null
)
